package com.dollop.fos.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.RestSaveRequest;
import com.dollop.fos.response.ViewRestaurantResponse;

public interface IRestaurantService {

	 public ResponseEntity<?> addRestaurant(RestSaveRequest rest,Principal p); 
	 public ResponseEntity<?> changeStatus(String id,boolean status);
	 public ResponseEntity<?> getDataofRestaurant(int page,int size);
	 public ResponseEntity<?> editRestaurant(ViewRestaurantResponse rest,Principal p);
	 public ResponseEntity<?> viewRestaurantOfOwner(Principal p);
	 public ResponseEntity<?> getRestaurantByRestId(String restId);
	 public ResponseEntity<?> deleteRestaurant(String restId);
	 public ResponseEntity<?> getRestaurauntNameAndCategoryofOwner(Principal p);
	 public ResponseEntity<?> getRestaurantNameOfOWner(Principal p);
	
}
