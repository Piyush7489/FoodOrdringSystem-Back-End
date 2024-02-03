package com.dollop.fos.serviceimpl;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.Category;
import com.dollop.fos.entity.Food;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.paginationdto.PageAllCategoryResponse;
import com.dollop.fos.paginationdto.PageFoodResponse;
import com.dollop.fos.paginationdto.PageRestaurantResponsee;
import com.dollop.fos.reposatory.ICategoryRepo;
import com.dollop.fos.reposatory.IFoodRepo;
import com.dollop.fos.reposatory.IRestaurantRepo;
import com.dollop.fos.requests.AddFoodRequest;
import com.dollop.fos.requests.CategorySaveRequest;
import com.dollop.fos.requests.RestaurantRequest;
import com.dollop.fos.response.AllCategoryResponse;
import com.dollop.fos.response.FoodResponse;
import com.dollop.fos.response.ViewRestaurantResponse;
import com.dollop.fos.service.IAdminService;
@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private IRestaurantRepo restRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private IFoodRepo frepo;
	
	@Autowired
	private ICategoryRepo crepo;

	
	@Override
	public ResponseEntity<?> verifyRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Optional<Restaurant> r= this.restRepo.findById(restId);
		if(r.isEmpty()) 
		{
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		else 
		{
			Restaurant restaurant = r.get();
			if(restaurant.getIsApprove().equals(AppConstant.VERIFIED)) 
			{
				response.put(AppConstant.DATA, AppConstant.ALREADY_VERIFIED);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else 
			{
				restaurant.setIsApprove(AppConstant.VERIFIED);
				restaurant.setRestId(restId);
				restaurant.setIsActive(true);
				this.restRepo.save(restaurant);
				response.put(AppConstant.DATA, AppConstant.RESTAURANT_VERIFIED_SUCCESSFULLY);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		
	}
	

	@Override
	public ResponseEntity<?> unVerifyRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Optional<Restaurant> r= this.restRepo.findById(restId);
		if(r.isEmpty()) 
		{
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		else 
		{
			Restaurant restaurant = r.get();
			if(restaurant.getIsApprove().equals(AppConstant.UNVERIFIED)) 
			{
				response.put(AppConstant.ERROR, AppConstant.REST_ALREADY_UNVERIFIED);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			else 
			{
				restaurant.setIsApprove(AppConstant.UNVERIFIED);
				restaurant.setRestId(restId);
				restaurant.setIsActive(false);
				this.restRepo.save(restaurant);
				response.put(AppConstant.DATA, AppConstant.RESTAURANT_UNVERIFIED_SUCCESSFULLY);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		
	}
	
	@Override
	public ResponseEntity<?> blockedRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Optional<Restaurant> r= this.restRepo.findById(restId);
		if(r.isEmpty()) 
		{
			response.put(AppConstant.ERROR,AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else 
		{
			Restaurant restaurant = r.get();
			if(restaurant.getIsBlocked().equals(AppConstant.BLOCKED)) 
			{
				response.put(AppConstant.ERROR, AppConstant.REST_ALERDY_BLOCK);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}else 
			{
				restaurant.setIsActive(false);
				restaurant.setIsBlocked(AppConstant.BLOCKED);
				restaurant.setRestId(restId);
				this.restRepo.save(restaurant);
				response.put(AppConstant.DATA, AppConstant.RESTAURANT_BLOCK_SUCCESSFULLY);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		
	}
	@Override
	public ResponseEntity<?> unblockedRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Optional<Restaurant> r= this.restRepo.findById(restId);
		if(r.isEmpty()) 
		{
			response.put(AppConstant.ERROR,AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else 
		{
			Restaurant restaurant = r.get();
			if(restaurant.getIsBlocked().equals(AppConstant.UNBLOCK)) 
			{
				response.put(AppConstant.ERROR, AppConstant.REST_ALERDY_UNBLOCK);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}else 
			{
				restaurant.setIsActive(true);
				restaurant.setIsBlocked(AppConstant.UNBLOCK);
				restaurant.setRestId(restId);
				this.restRepo.save(restaurant);
				response.put(AppConstant.DATA, AppConstant.RESTAURANT_UNBLOCK_SUCCESSFULLY);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		
	}
	@Override
	public ResponseEntity<?> viewAllRestaurant(int pageno,int pageSize,String sortBy,RestaurantRequest request) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("restId", match->match.transform(value->value.map(id->(id=="")?null:id)));
		Example<Restaurant> example = Example.of(restaurantRequestToRestaurant(request),exampleMatcher);
		Pageable pageable = PageRequest.of(pageno, pageSize,Sort.Direction.ASC,sortBy);
		Page<Restaurant> findAllRestaurant = this.restRepo.findAll(example,pageable);
		Page<ViewRestaurantResponse> map = findAllRestaurant.map(p-> restToViewRestResponse(p));
		List<ViewRestaurantResponse> content = map.getContent();
		List<ViewRestaurantResponse> newList = null;
		if (content != null && !content.isEmpty()) {
 			newList =  new ArrayList<>(content);
 		    Collections.reverse(newList);
 		    PageRestaurantResponsee prr = new PageRestaurantResponsee();
 		    System.out.println(newList);
 			prr.setContents(newList);
 			prr.setTotalElements(findAllRestaurant.getTotalElements());
 			response.put(AppConstant.DATA, prr);
 			return ResponseEntity.status(HttpStatus.OK).body(response);
 		}
 		else
 		{
 			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
 			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
 		}
	
	}
	public Restaurant restaurantRequestToRestaurant(RestaurantRequest restaurantrequest) 
	{
		return this.modelMapper.map(restaurantrequest, Restaurant.class);
	}
	private ViewRestaurantResponse restToViewRestResponse(Restaurant r) {
		ViewRestaurantResponse v = new ViewRestaurantResponse();
		v.setRestId(r.getRestId());
		v.setRestName(r.getRestName());
		v.setCurrentStatus(r.getCurrentStatus());
		v.setIsActive(r.getIsActive());
		v.setIsBlocked(r.getIsBlocked());
		v.setIsApprove(r.getIsApprove());
		v.setRestCloseTime(r.getRestCloseTime());
		v.setRestOpenTime(r.getRestOpenTime());
		v.setRestDescription(r.getRestDescription());
		return v;
	}
	@Override
	public ResponseEntity<?> viewAllVerifiedRestaurant(int pageNo, int pageSize, String sortBy,RestaurantRequest request) {
		Map<String,Object> response = new HashMap<>();
		request.setIsApprove(AppConstant.VERIFIED);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("restId", match->match.transform(value->value.map(id->(id=="")?null:id)));
		Example<Restaurant> example = Example.of(restaurantRequestToRestaurant(request),exampleMatcher);
		Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.Direction.ASC,sortBy);
		Page<Restaurant> findAllRestaurant = this.restRepo.findAll(example,pageable);
		Page<ViewRestaurantResponse> map = findAllRestaurant.map(p-> restToViewRestResponse(p));
		List<ViewRestaurantResponse> content = map.getContent();
		List<ViewRestaurantResponse> newList = null;
		if (content != null && !content.isEmpty()) {
 			newList =  new ArrayList<>(content);
 		    Collections.reverse(newList);
 		    PageRestaurantResponsee prr = new PageRestaurantResponsee();
 		    System.out.println(newList);
 			prr.setContents(newList);
 			prr.setTotalElements(findAllRestaurant.getTotalElements());
 			response.put(AppConstant.DATA, prr);
 			return ResponseEntity.status(HttpStatus.OK).body(response);
 		}
 		else
 		{
 			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
 			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
 		}
	
	}
	@Override
	public ResponseEntity<?> viewAllUnVerifiedRestaurant(int pageNo, int pageSize, String sortBy,RestaurantRequest request) {
		Map<String,Object> response = new HashMap<>();
		request.setIsApprove(AppConstant.UNVERIFIED);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("restId", match->match.transform(value->value.map(id->(id=="")?null:id)));
		Example<Restaurant> example = Example.of(restaurantRequestToRestaurant(request),exampleMatcher);
		Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.Direction.ASC,sortBy);
		Page<Restaurant> findAllRestaurant = this.restRepo.findAll(example,pageable);
		Page<ViewRestaurantResponse> map = findAllRestaurant.map(p-> restToViewRestResponse(p));
		List<ViewRestaurantResponse> content = map.getContent();
		List<ViewRestaurantResponse> newList = null;
		if (content != null && !content.isEmpty()) {
 			newList =  new ArrayList<>(content);
 		    Collections.reverse(newList);
 		    PageRestaurantResponsee prr = new PageRestaurantResponsee();
 		    System.out.println(newList);
 			prr.setContents(newList);
 			prr.setTotalElements(findAllRestaurant.getTotalElements());
 			response.put(AppConstant.DATA, prr);
 			return ResponseEntity.status(HttpStatus.OK).body(response);
 		}
 		else
 		{
 			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
 			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
 		}
	
	}
	@Override
	public ResponseEntity<?> viewAllUnBlockRestaurant(int pageNo, int pageSize, String sortBy,RestaurantRequest request) {
		Map<String,Object> response = new HashMap<>();
		request.setIsApprove(AppConstant.UNBLOCK);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("restId", match->match.transform(value->value.map(id->(id=="")?null:id)));
		Example<Restaurant> example = Example.of(restaurantRequestToRestaurant(request),exampleMatcher);
		Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.Direction.ASC,sortBy);
		Page<Restaurant> findAllRestaurant = this.restRepo.findAll(example,pageable);
		Page<ViewRestaurantResponse> map = findAllRestaurant.map(p-> restToViewRestResponse(p));
		List<ViewRestaurantResponse> content = map.getContent();
		List<ViewRestaurantResponse> newList = null;
		if (content != null && !content.isEmpty()) {
 			newList =  new ArrayList<>(content);
 		    Collections.reverse(newList);
 		    PageRestaurantResponsee prr = new PageRestaurantResponsee();
 		    System.out.println(newList);
 			prr.setContents(newList);
 			prr.setTotalElements(findAllRestaurant.getTotalElements());
 			response.put(AppConstant.DATA, prr);
 			return ResponseEntity.status(HttpStatus.OK).body(response);
 		}
 		else
 		{
 			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
 			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
 		}
	
	}
	@Override
	public ResponseEntity<?> viewAllBlockRestaurant(int pageNo, int pageSize, String sortBy,RestaurantRequest request) {
		Map<String,Object> response = new HashMap<>();
		request.setIsApprove(AppConstant.BLOCKED);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("restId", match->match.transform(value->value.map(id->(id=="")?null:id)));
		Example<Restaurant> example = Example.of(restaurantRequestToRestaurant(request),exampleMatcher);
		Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.Direction.ASC,sortBy);
		Page<Restaurant> findAllRestaurant = this.restRepo.findAll(example,pageable);
		Page<ViewRestaurantResponse> map = findAllRestaurant.map(p-> restToViewRestResponse(p));
		List<ViewRestaurantResponse> content = map.getContent();
		List<ViewRestaurantResponse> newList = null;
		if (content != null && !content.isEmpty()) {
 			newList =  new ArrayList<>(content);
 		    Collections.reverse(newList);
 		    PageRestaurantResponsee prr = new PageRestaurantResponsee();
 		    System.out.println(newList);
 			prr.setContents(newList);
 			prr.setTotalElements(findAllRestaurant.getTotalElements());
 			response.put(AppConstant.DATA, prr);
 			return ResponseEntity.status(HttpStatus.OK).body(response);
 		}
 		else
 		{
 			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
 			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
 		}
	
	}

	@Override
	public ResponseEntity<?> viewAllFood(int pageNo, int pageSize, String sortBy, AddFoodRequest request) {
		// TODO Auto-generated method stub
		request.setFoodId(null);
		Food requestToFood = resquestToFood(request);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("foodId", match->match.transform(value->value.map(id->(((Long)id).intValue()==0)?null:((Long)id).intValue())));
		Example<Food> example = Example.of(requestToFood, exampleMatcher);
		Pageable pagebale = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "foodId");
		Page<Food> findAll = this.frepo.findAll(example, pagebale);
		Page<FoodResponse> map = findAll.map(food->foodToResponse(food));
		PageFoodResponse pfr = new PageFoodResponse();
		pfr.setContents(map.getContent());
		pfr.setTotalElements(map.getTotalElements());
		return ResponseEntity.status(HttpStatus.OK).body(pfr);
	
	}
	@Override
	public ResponseEntity<?> viewAllActiveFood(int pageNo, int pageSize, String sortBy, AddFoodRequest request) {
		// TODO Auto-generated method stub
		request.setIsAvailable(true);
		Food requestToFood = resquestToFood(request);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("foodId", match->match.transform(value->value.map(id->(((Long)id).intValue()==0)?null:((Long)id).intValue())));
		Example<Food> example = Example.of(requestToFood, exampleMatcher);
		Pageable pagebale = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "foodId");
		Page<Food> findAll = this.frepo.findAll(example, pagebale);
		Page<FoodResponse> map = findAll.map(food->foodToResponse(food));
		PageFoodResponse pfr = new PageFoodResponse();
		pfr.setContents(map.getContent());
		pfr.setTotalElements(map.getTotalElements());
		return ResponseEntity.status(HttpStatus.OK).body(pfr);
		
	}
	@Override
	public ResponseEntity<?> viewAllInActiveFood(int pageNo, int pageSize, String sortBy, AddFoodRequest request) {
		request.setIsAvailable(false);
		Food requestToFood = resquestToFood(request);
		ExampleMatcher exampleMatcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(StringMatcher.CONTAINING)
				.withIgnoreCase()
				.withMatcher("foodId", match->match.transform(value->value.map(id->(((Long)id).intValue()==0)?null:((Long)id).intValue())));
		Example<Food> example = Example.of(requestToFood, exampleMatcher);
		Pageable pagebale = PageRequest.of(pageNo, pageSize, Sort.Direction.ASC, "foodId");
		Page<Food> findAll = this.frepo.findAll(example, pagebale);
		Page<FoodResponse> map = findAll.map(food->foodToResponse(food));
		PageFoodResponse pfr = new PageFoodResponse();
		pfr.setContents(map.getContent());
		pfr.setTotalElements(map.getTotalElements());
		return ResponseEntity.status(HttpStatus.OK).body(pfr);
	}
	
	@Override 
	public ResponseEntity<?> viewAllGlobalCategory(int pageNo, int pageSize, String sortBy,
			CategorySaveRequest request) {
		// TODO Auto-generated method stub
		
		return null;
	}
	public Food resquestToFood(AddFoodRequest foodRequest) 
	{
		return this.modelMapper.map(foodRequest, Food.class);
	}
	public FoodResponse foodToResponse(Food food) 
	{
		return this.modelMapper.map(food, FoodResponse.class);
	}


	public Category CSRToCategory(CategorySaveRequest csr)
	{
		return this.modelMapper.map(csr, Category.class);
	}
	
	public AllCategoryResponse CategoryToACR(Category cat)
	{
//		String email = cat.getRestaurant().getOwner().getEmail();
		AllCategoryResponse map = this.modelMapper.map(cat, AllCategoryResponse.class);
//		map.setOwnerEmail(email);
		return map;
	}


	

	
}
