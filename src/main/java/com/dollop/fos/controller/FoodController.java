package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dollop.fos.requests.AddFoodRequest;
import com.dollop.fos.service.IFoodService;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

	@Autowired
	private IFoodService service;
	
//    @PostMapping("/save")
//	public ResponseEntity<?> addFood(AddFoodRequest addFoodRequest)
//	{
//		return this.service.addFood(addFoodRequest);
//	}
    
}
