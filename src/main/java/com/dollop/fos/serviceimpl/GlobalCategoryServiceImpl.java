package com.dollop.fos.serviceimpl;

import java.io.IOException;
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
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.entity.GlobalCategory;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.entity.RestaurantCategory;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.helper.FolderName;
import com.dollop.fos.reposatory.IGlobalCaregoryRepo;
import com.dollop.fos.reposatory.IRestaurantCategoryRepo;
import com.dollop.fos.reposatory.IRestaurantRepo;
import com.dollop.fos.requests.GlobalCategoryRequest;
import com.dollop.fos.requests.RestCategoryRequest;
import com.dollop.fos.response.GlobalCategoryNameResponse;
import com.dollop.fos.response.GlobalCategoryResponse;
import com.dollop.fos.service.IGlobalCategoryService;
import com.dollop.fos.utility.IImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service

public class GlobalCategoryServiceImpl implements IGlobalCategoryService{
    @Autowired
	private IGlobalCaregoryRepo repo;
    @Autowired
    private IImageService service;
    @Autowired
    private ModelMapper mapper;
	@Autowired
    private ObjectMapper obMapper;
	@Autowired
	private IRestaurantRepo restRepo;
	@Autowired
	private IRestaurantCategoryRepo rcRepo;
	
	@Override
	public ResponseEntity<?> createGlobalCategory(String globalCategoryRequest,MultipartFile catImage) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		GlobalCategoryRequest request = null;
		try {
			request = this.obMapper.readValue(globalCategoryRequest, GlobalCategoryRequest.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		GlobalCategory g1 = this.repo.findbyCatName(request.getCatName());
		if(g1!=null) 
		{
		  response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY__FOUND);
		  return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		else {
			GlobalCategory g = this.RequestToGlobalCategory(request);
			String imageName=null;
			try {
				imageName = this.service.uploadImage(catImage, FolderName.CATEGORY_PHOTO);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.setCatImage(imageName);
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
		List<GlobalCategoryResponse> view = list.stream().map(category->new GlobalCategoryResponse(category.getCatId(), category.getCatName(), null, null,null)).collect(Collectors.toList());
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

	@Override
	public ResponseEntity<?> getAllCategoryWhoNotHaveThisRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		List<GlobalCategory> list = this.repo.getCategoryWhoNotExistThisRestaurantWhoIdIPass(restId);
		response.put(AppConstant.RESPONSE_MESSAGE, list);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@Override
	public ResponseEntity<?> addCategoryInRestaurant(RestCategoryRequest request) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		RestaurantCategory r = SetDataInResCategory(request);
		RestaurantCategory r1= this.rcRepo.save(r);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_ADD_SUCCESS);
		response.put(AppConstant.DATA, r1);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	private RestaurantCategory SetDataInResCategory(RestCategoryRequest request) {
		// TODO Auto-generated method stub
		RestaurantCategory rc = new RestaurantCategory();
		GlobalCategory gc = this.repo.findWithId(request.getCatId());
		Restaurant r = this.restRepo.findByRestId(request.getRestaurant_id());
		rc.setGlobalCategory(gc);
		rc.setRestaurant(r);
		rc.setId(UUID.randomUUID().toString());
		return rc;
	}

	

}
