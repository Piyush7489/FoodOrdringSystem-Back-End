package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.AddFoodRequest;

public interface IFoodService {

	public ResponseEntity<?> addFood(AddFoodRequest request);
	
}
