package com.dollop.fos.serviceimpl;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.entity.Food;
import com.dollop.fos.entity.GlobalCategory;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.entity.RestaurantCategory;
import com.dollop.fos.entity.User;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.helper.FolderName;
import com.dollop.fos.reposatory.IFoodRepo;
import com.dollop.fos.reposatory.IRestaurantCategoryRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.requests.AddFoodRequest;
import com.dollop.fos.requests.UpdateFoodRequest;
import com.dollop.fos.response.ViewFoodResponse;
import com.dollop.fos.service.IFoodService;
import com.dollop.fos.utility.IImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class FoodServiceImpl implements IFoodService {
    @Autowired
	private ObjectMapper mapper;
    @Autowired
    private IFoodRepo frepo;
    @Autowired
    private IImageService service;
    @Autowired    
    private IRestaurantCategoryRepo rcRepo;
    @Autowired
    private IUserRepo uRepo;
	@Override
	public ResponseEntity<?> addFood(String foodRequest, MultipartFile foodImage) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		AddFoodRequest food=null;
		try {
			food= mapper.readValue(foodRequest, AddFoodRequest.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   Food f = this.frepo.findFoodByFoodName(food.getFoodName(),food.getRestcategoryId());
	   
	   if(f!=null) 
	   {
		   response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.FOUND_FOUND);
		   return ResponseEntity.status(HttpStatus.OK).body(response);
	   }
		   Food f1 = setDataInFood(food,foodImage);
		   this.frepo.save(f1);
		   response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.FOOD_ADD_SUCCESS);
		   return ResponseEntity.status(HttpStatus.OK).body(response);
	
		
	}
	private Food setDataInFood(AddFoodRequest food,MultipartFile foodimage) {
		// TODO Auto-generated method stub
		Food f = new Food();
		f.setFoodCreatedAt(LocalDateTime.now());
		f.setFoodDescription(food.getFoodDescription());
		f.setFoodId(UUID.randomUUID().toString());
		f.setFoodPrice(food.getFoodPrice());
		f.setIsAvailable(true);	
		RestaurantCategory rc = this.rcRepo.findByRestCatId(food.getRestaurantId(),food.getGlobalCategoryId());
		f.setRestCategory(rc);
		f.setFoodName(food.getFoodName());
		String imagename=null;
		try {
			imagename = this.service.uploadImage(foodimage, FolderName.FOOD);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		f.setImageName(imagename);
		return f;
	}
	@Override
	public ResponseEntity<?> viewAllFood(int page, int size,Principal p) {
		// TODO Auto-generated method stub
		 Map<String,Object> response = new HashMap<>();
		 User owner = this.uRepo.findByEmail(p.getName());
		 Pageable pageable = PageRequest.of(page, size);
		 Page<Food> food = this.frepo.findAll(pageable);
		 List<ViewFoodResponse> viewFoodResponse = food.getContent().stream()
				 .map(this::foodToViewFoodResponse)
				 .filter(v->owner.getUserId().equals(v.getOwnerId()))
				 .collect(Collectors.toList());
		 if(viewFoodResponse.isEmpty()) 
		 {
			 response.put(AppConstant.RESPONSE_MESSAGE, null);
		        return ResponseEntity.status(HttpStatus.OK).body(response);
		 }
		 Page<ViewFoodResponse> pageResponse = new PageImpl<>(viewFoodResponse, pageable, food.getTotalElements());
		    response.put(AppConstant.RESPONSE_MESSAGE, pageResponse);
		    return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	private ViewFoodResponse foodToViewFoodResponse(Food f) 
	{
		ViewFoodResponse v = new ViewFoodResponse();
		v.setFoodId(f.getFoodId());
		v.setFoodCreatedAt(f.getFoodCreatedAt());
		v.setFoodDescription(f.getFoodDescription());
		v.setFoodName(f.getFoodName());
		v.setFoodPrice(f.getFoodPrice());
		v.setImageName(f.getImageName());
		v.setIsAvailable(f.getIsAvailable());
		RestaurantCategory rc = f.getRestCategory();
		v.setRestCategoryId(rc.getId());
		Restaurant r = rc.getRestaurant();
		
		GlobalCategory g = rc.getGlobalCategory();
		
		v.setFoodCategoryName(g.getCatName());
		v.setFoodRestaurantname(r.getRestName());
		v.setOwnerId(r.getOwner().getUserId());
		return v;
	}
	@Override
	public ResponseEntity<?> updateFood(String request, MultipartFile foodImage) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		UpdateFoodRequest u=null;
		try {
		u=mapper.readValue(request, UpdateFoodRequest.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Food f = this.frepo.findFoodByFoodNameButNotFoodId(u.getFoodName(),u.getRestCategoryid(),u.getFoodId());
		Food f1 = this.frepo.findFoodByFoodId(u.getFoodId());
		if(f!=null) 
		{
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.FOUND_FOUND);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else
		{
			Food food = setDataInFood(u,foodImage,f1);
			this.frepo.save(food);		
			response.put(AppConstant.RESPONSE_MESSAGE,AppConstant.FOOD_UPDATE_SUCCESS);
			response.put(AppConstant.DATA, food);
			return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		
	}
	private Food setDataInFood(UpdateFoodRequest u,MultipartFile foodimage,Food existfood) {
		// TODO Auto-generated method stub
		Food newfood = new  Food();
		newfood.setFoodDescription(u.getFoodDescription());
		newfood.setFoodId(u.getFoodId());
		newfood.setFoodName(u.getFoodName());
		newfood.setFoodPrice(u.getFoodPrice());
		newfood.setFoodCreatedAt(existfood.getFoodCreatedAt());
		newfood.setIsAvailable(existfood.getIsAvailable());
		RestaurantCategory rc = existfood.getRestCategory();
		newfood.setRestCategory(rc);
		String image;
		if(Objects.nonNull(foodimage)) 
		{
			try {
				image=	this.service.uploadImage(foodimage, FolderName.FOOD);
				newfood.setImageName(image);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else 
		{
			newfood.setImageName(existfood.getImageName());
		}
		return newfood;
	}
	

	

}
