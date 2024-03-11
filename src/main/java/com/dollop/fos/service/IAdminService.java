package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.AddFoodRequest;
import com.dollop.fos.requests.CategorySaveRequest;
import com.dollop.fos.requests.RestaurantRequest;



public interface IAdminService {

	
	public ResponseEntity<?> verifyRestaurant(String restId);
	public ResponseEntity<?> unVerifyRestaurant(String restId);
	public ResponseEntity<?> blockedRestaurant(String restId);
	public ResponseEntity<?> unblockedRestaurant(String restId);
	public ResponseEntity<?> viewAllCategory();
	public ResponseEntity<?> viewAllRestaurant();
	public ResponseEntity<?> viewVerifiedRestaurant();
	public ResponseEntity<?> verificationOfRestaurant(String id);
	public ResponseEntity<?> getCustomerList(int page,int size);
	public ResponseEntity<?> getOwnerList(int page,int size);
	public ResponseEntity<?> getAllDeliveryBoyList(int page,int size);
	public ResponseEntity<?> getAllRestaurantofOwnerId(String ownerId);
}
