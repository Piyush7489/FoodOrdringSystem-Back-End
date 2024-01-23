package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.RestaurantRequest;



public interface IAdminService {

	public ResponseEntity<?> verifyRestaurant(String restId);
	public ResponseEntity<?> unVerifyRestaurant(String restId);
	public ResponseEntity<?> blockedRestaurant(String restId);
	public ResponseEntity<?> unblockedRestaurant(String restId);
	public ResponseEntity<?> viewAllRestaurant(int pageNo,int pageSize,String sortBy,RestaurantRequest request);
	public ResponseEntity<?> viewAllVerifiedRestaurant(int pageNo,int pageSize,String sortBy,RestaurantRequest request);
    public ResponseEntity<?> viewAllUnVerifiedRestaurant(int pageNo,int pageSize,String sortBy,RestaurantRequest request);
    public ResponseEntity<?> viewAllUnBlockRestaurant(int pageNo,int pageSize,String sortBy,RestaurantRequest request);
    public ResponseEntity<?> viewAllBlockRestaurant(int pageNo,int pageSize,String sortBy,RestaurantRequest request);
    
} 
