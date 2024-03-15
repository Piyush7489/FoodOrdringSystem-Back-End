package com.dollop.fos.serviceimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.Food;
import com.dollop.fos.entity.GlobalCategory;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.entity.User;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IGlobalCaregoryRepo;
import com.dollop.fos.reposatory.IRestaurantRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.requests.AddFoodRequest;
import com.dollop.fos.requests.RestaurantRequest;
import com.dollop.fos.requests.RestaurantVerificationRequest;
import com.dollop.fos.response.CountOfCustomerAndBoyResponse;
import com.dollop.fos.response.FoodResponse;
import com.dollop.fos.response.OwnerResponse;
import com.dollop.fos.response.UserResponse;
import com.dollop.fos.response.ViewRestaurantOfOwnerByAdmin;
import com.dollop.fos.response.ViewRestaurantResponse;
import com.dollop.fos.service.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private IRestaurantRepo restRepo;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private IGlobalCaregoryRepo grepo;
	@Autowired
	private IUserRepo urepo;

	@Override
	public ResponseEntity<?> verifyRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Optional<Restaurant> r = this.restRepo.findById(restId);
		if (r.isEmpty()) {
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} else {
			Restaurant restaurant = r.get();
			if (restaurant.getIsApprove().equals(AppConstant.VERIFIED)) {
				response.put(AppConstant.DATA, AppConstant.ALREADY_VERIFIED);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
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
		Map<String, Object> response = new HashMap<>();
		Optional<Restaurant> r = this.restRepo.findById(restId);
		if (r.isEmpty()) {
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} else {
			Restaurant restaurant = r.get();
			if (restaurant.getIsApprove().equals(AppConstant.UNVERIFIED)) {
				response.put(AppConstant.ERROR, AppConstant.REST_ALREADY_UNVERIFIED);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
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
		Map<String, Object> response = new HashMap<>();
		Optional<Restaurant> r = this.restRepo.findById(restId);
		if (r.isEmpty()) {
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} else {
			Restaurant restaurant = r.get();
			if (restaurant.getIsBlocked().equals(AppConstant.BLOCKED)) {
				response.put(AppConstant.ERROR, AppConstant.REST_ALERDY_BLOCK);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
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
		Map<String, Object> response = new HashMap<>();
		Optional<Restaurant> r = this.restRepo.findById(restId);
		if (r.isEmpty()) {
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		} else {
			Restaurant restaurant = r.get();
			if (restaurant.getIsBlocked().equals(AppConstant.UNBLOCK)) {
				response.put(AppConstant.ERROR, AppConstant.REST_ALERDY_UNBLOCK);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
				restaurant.setIsActive(true);
				restaurant.setIsBlocked(AppConstant.UNBLOCK);
				restaurant.setRestId(restId);
				this.restRepo.save(restaurant);
				response.put(AppConstant.DATA, AppConstant.RESTAURANT_UNBLOCK_SUCCESSFULLY);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}

	}

	public Restaurant restaurantRequestToRestaurant(RestaurantRequest restaurantrequest) {
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

	public Food resquestToFood(AddFoodRequest foodRequest) {
		return this.modelMapper.map(foodRequest, Food.class);
	}

	public FoodResponse foodToResponse(Food food) {
		return this.modelMapper.map(food, FoodResponse.class);
	}

	@Override
	public ResponseEntity<?> viewAllCategory() {
		// TODO Auto-generated method stub

		List<GlobalCategory> g = this.grepo.findAll();
		Map<String, Object> response = new HashMap<>();
		response.put(AppConstant.RESPONSE_MESSAGE, g);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<?> viewAllRestaurant() {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		List<Restaurant> restaurant = this.restRepo.findAll();
		List<ViewRestaurantResponse> view = this.RestaurantToRestaurantResponse(restaurant);
		response.put(AppConstant.RESPONSE_MESSAGE, view);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private List<ViewRestaurantResponse> RestaurantToRestaurantResponse(List<Restaurant> restaurant) {
		// TODO Auto-generated method stub
		return restaurant.stream().map(this::restToViewRestResponse).collect(Collectors.toList());
	}

	@Override
	public ResponseEntity<?> viewVerifiedRestaurant() {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		List<Restaurant> restaurant = this.restRepo.findAllVerifiedRestaurant(AppConstant.VERIFIED);
		List<ViewRestaurantResponse> view = this.RestaurantToRestaurantResponse(restaurant);
		response.put(AppConstant.RESPONSE_MESSAGE, view);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<?> verificationOfRestaurant(String id) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Restaurant r = this.restRepo.findByRestId(id);
		RestaurantVerificationRequest re = setDataInVerification(r);
		response.put(AppConstant.RESPONSE_MESSAGE, re);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private RestaurantVerificationRequest setDataInVerification(Restaurant r) {
		// TODO Auto-generated method stub
		RestaurantVerificationRequest r1 = new RestaurantVerificationRequest();
		r1.setFssaiLicenceNo(r.getFssaiLicense().getLicenseNumber());
		r1.setFssaiLicencePhoto(r.getFssaiLicense().getFssaiLicensePhoto());
		r1.setGstLicencePhoto(r.getGstRegistration().getGstlicensePhoto());
		r1.setGstlicenseNo(r.getGstRegistration().getLicenseNumber());
		r1.setRestid(r.getRestId());
		r1.setRestname(r.getRestName());
		r1.setRestImage(r.getRestImageName());
		return r1;
	}

	@Override
	public ResponseEntity<?> getCustomerList(int page, int size) {
		// TODO Auto-generated method stub

		Map<String, Object> response = new HashMap<>();
		Pageable pageable = PageRequest.of(page, size);
		Page<User> customer = this.urepo.findByRoleName("CUSTOMER", pageable);
		System.err.println(customer.getContent());
		List<UserResponse> customerlist = customer.getContent().stream().map(this::userToUserResponse)
				.collect(Collectors.toList());
		Page page1 = new PageImpl<>(customerlist, pageable, customer.getTotalElements());
		response.put(AppConstant.RESPONSE_MESSAGE, page1);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	private UserResponse userToUserResponse(User u) {
		UserResponse userResponse = new UserResponse();
		userResponse.setUserId(u.getUserId());
		userResponse.setFirstName(u.getFirstName());
		userResponse.setLastName(u.getLastName());
		userResponse.setMob(u.getMob());
		userResponse.setCreateAt(u.getCreateAt());
		userResponse.setProfilePhoto(u.getProfilePhoto());
		userResponse.setTempAddress(u.getTempAddress());
		userResponse.setIsActive(u.getIsActive());
		userResponse.setEmail(u.getEmail());
		return userResponse;
	}

	@Override
	public ResponseEntity<?> getOwnerList(int page, int size) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		Pageable pageable = PageRequest.of(page, size);
		Page<User> owner = this.urepo.findByRoleName("OWNER", pageable);

		List<OwnerResponse> ownerList = owner.getContent().stream().map(this::ownerToOwnerResponse)
				.collect(Collectors.toList());
		Page page1 = new PageImpl<>(ownerList, pageable, owner.getTotalElements());
		response.put(AppConstant.RESPONSE_MESSAGE, page1);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private OwnerResponse ownerToOwnerResponse(User u) {
		OwnerResponse ownerResponse = new OwnerResponse();
		ownerResponse.setUserId(u.getUserId());
		ownerResponse.setCreateAt(u.getCreateAt());
		ownerResponse.setEmail(u.getEmail());
		ownerResponse.setFirstName(u.getFirstName());
		ownerResponse.setLastName(u.getLastName());
		ownerResponse.setMob(u.getMob());
		ownerResponse.setProfilePhoto(u.getProfilePhoto());
		ownerResponse.setTempAddress(u.getTempAddress());
		return ownerResponse;
	}

	@Override
	public ResponseEntity<?> getAllRestaurantofOwnerId(String ownerId) {
		// TODO Auto-generated method stub
		Map<String, Object> response = new HashMap<>();
		List<Restaurant> list = this.restRepo.getRestaurantByOwnerId(ownerId);
		List<ViewRestaurantOfOwnerByAdmin> viewList = list.stream().map(this::restToviewRestResponseofOwner)
				.collect(Collectors.toList());
		response.put(AppConstant.RESPONSE_MESSAGE, viewList);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private ViewRestaurantOfOwnerByAdmin restToviewRestResponseofOwner(Restaurant r) {
		ViewRestaurantOfOwnerByAdmin v = new ViewRestaurantOfOwnerByAdmin();
		v.setRestId(r.getRestId());
		v.setCity(r.getRestAddress().getCity());
		v.setCreatedat(r.getCreatedAt());
		v.setCurrentStatus(r.getCurrentStatus());
		v.setFssaiLicenceNo(r.getFssaiLicense().getLicenseNumber());
		v.setFssaiLicencePhoto(r.getFssaiLicense().getFssaiLicensePhoto());
		v.setGstLicenceNo(r.getGstRegistration().getLicenseNumber());
		v.setGstLicencePhoto(r.getFssaiLicense().getFssaiLicensePhoto());
		v.setIsActive(r.getIsActive());
		v.setRestCloseTime(r.getRestCloseTime());
		v.setRestContect(r.getRestAddress().getRestContect());
		v.setRestOpenTime(r.getRestOpenTime());
		v.setRestImage(r.getRestImageName());
		v.setRestName(r.getRestName());
		v.setRestDescription(r.getRestDescription());
		v.setState(r.getRestAddress().getState());
		v.setIsApprove(r.getIsApprove());
		return v;
	}

	@Override
	public ResponseEntity<?> getAllDeliveryBoyList(int page, int size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> getCountOfCustomersAndBoy() {
		Map<String, Long> countOfCustomerAndBoy = this.urepo.getCountOfCustomerAndBoy();
		Map<String, Object> response = new HashMap<>();
		if (Objects.nonNull(countOfCustomerAndBoy)) {
			CountOfCustomerAndBoyResponse count = changeMapToUserCount(countOfCustomerAndBoy);
			response.put(AppConstant.DATA, count);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		response.put(AppConstant.ERROR, AppConstant.COUNT_OF_CUSTOMER_AND_BOY_NOT_FOUND);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	public CountOfCustomerAndBoyResponse changeMapToUserCount(Map<String, Long> map) {
		CountOfCustomerAndBoyResponse response = new CountOfCustomerAndBoyResponse();
		response.setBoyCount(map.get("boyCount"));
		response.setCustomerCount(map.get("customerCount"));
		response.setOwnerCount(map.get("ownerCount"));
		response.setTotalCountOfUser(map.get("totalCountOfUser"));
		return response;
	}

}
