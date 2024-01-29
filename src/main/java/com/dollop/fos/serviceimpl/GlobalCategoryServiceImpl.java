package com.dollop.fos.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.GlobalCategory;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IGlobalCaregoryRepo;
import com.dollop.fos.requests.GlobalCategoryRequest;
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


	private GlobalCategory RequestToGlobalCategory(GlobalCategoryRequest request) {
		// TODO Auto-generated method stub
		return this.mapper.map(request, GlobalCategory.class);
	}

}
