package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;



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
	public ResponseEntity<?> getAllFood(int page,int size);
	public ResponseEntity<?> getCountOfCustomersAndBoy();
}
