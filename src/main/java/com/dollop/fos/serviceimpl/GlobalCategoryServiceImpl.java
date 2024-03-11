package com.dollop.fos.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.GlobalCategory;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IGlobalCaregoryRepo;
import com.dollop.fos.requests.GlobalCategoryRequest;
import com.dollop.fos.response.GlobalCategoryNameResponse;
import com.dollop.fos.response.GlobalCategoryResponse;
import com.dollop.fos.service.IGlobalCategoryService;
@Service

public class GlobalCategoryServiceImpl implements IGlobalCategoryService{
    @Autowired
	private IGlobalCaregoryRepo repo;
    
    
    @Autowired
    private ModelMapper mapper;
	
	
	@Override
	public ResponseEntity<?> createGlobalCategory(GlobalCategoryRequest request) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		GlobalCategory g = this.RequestToGlobalCategory(request);
		GlobalCategory g1 = this.repo.findbyCatName(g.getCatName());
		if(g1!=null) 
		{
		  response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY__FOUND);
		  return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else {
			g.setCatId(UUID.randomUUID().toString());
			g.setIsActive(true);
			this.repo.save(g);
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_ADD_SUCCESS);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
	
	}


	@Override
	public ResponseEntity<?> updateGlobalCategory(GlobalCategoryRequest request) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		
		GlobalCategory g = this.repo.findWithId(request.getCatId());
		GlobalCategory g1 = this.repo.findbyCatNameNotThisId(request.getCatName(),request.getCatId());
		if(g1!=null) 
		{
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY__FOUND);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else if(g!=null) 
		{
			g.setCatDescription(request.getCatDescription());
			g.setCatId(request.getCatId());
			g.setCatName(request.getCatName());
			g.setIsActive(request.getIsActive());
			this.repo.save(g);
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_UPDATE_SUCCESS);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else {
			response.put(AppConstant.ERROR, AppConstant.CATEGORY_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
	}

	private GlobalCategory RequestToGlobalCategory(GlobalCategoryRequest request) {
		// TODO Auto-generated method stub
		return this.mapper.map(request, GlobalCategory.class);
	}


	@Override
	public ResponseEntity<?> getGlobalCategoryById(String id) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		GlobalCategory g = this.repo.findWithId(id);
		if(g==null) 
		{
			response.put(AppConstant.ERROR, AppConstant.CATEGORY_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		else {
		GlobalCategoryResponse gr = setData(g);
		response.put(AppConstant.RESPONSE_MESSAGE, gr);
		return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		
	}


	private GlobalCategoryResponse setData(GlobalCategory g) {
		// TODO Auto-generated method stub
		GlobalCategoryResponse r = new GlobalCategoryResponse();
		r.setCatDescription(g.getCatDescription());
		r.setCatId(g.getCatId());
		r.setCatName(g.getCatName());
		r.setIsActive(g.getIsActive());
		return r;
	}


	@Override
	public ResponseEntity<?> deleteGlobalCategory(String id) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		GlobalCategory g = this.repo.findWithId(id);
		this.repo.delete(g);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@Override
	public ResponseEntity<?> updateActiveStatus(String id, boolean active) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		GlobalCategory g = this.repo.findWithId(id);
		g.setCatId(id);
		g.setIsActive(active);
		this.repo.save(g);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_UPDATE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@Override
	public ResponseEntity<?> getData(int page, int size) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		 Pageable pageable = PageRequest.of(page, size);
		Page <GlobalCategory>globalcategory= repo.findAll(pageable);
		
	    response.put(AppConstant.RESPONSE_MESSAGE, globalcategory);
	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@Override
	public ResponseEntity<?> getAllActiveCategoryName() {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		List<GlobalCategory> list = this.repo.findByIsActiveIsTrue();
		List<GlobalCategoryResponse> view = list.stream().map(category->new GlobalCategoryResponse(category.getCatId(), category.getCatName(), null, null)).collect(Collectors.toList());
		response.put(AppConstant.RESPONSE_MESSAGE, view);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@Override
	public ResponseEntity<?> getCategoryByRestId(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
	   List<GlobalCategoryNameResponse> list = this.repo.findCatIdAndCatNameByRestaurantId(restId);
	    response.put(AppConstant.RESPONSE_MESSAGE, list);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}



	

}
