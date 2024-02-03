package com.dollop.fos.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.RestSaveRequest;

public interface IRestaurantService {

	 public ResponseEntity<?> addRestaurant(RestSaveRequest rest,Principal p); 
	
	 
}
