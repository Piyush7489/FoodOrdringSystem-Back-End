package com.dollop.fos.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.RestSaveRequest;
import com.dollop.fos.service.IRestaurantService;

@RestController
@RequestMapping("/api/v1/rest")
public class RestaurantController {

	@Autowired
	private IRestaurantService service;
	


	@PostMapping("/save")
	public ResponseEntity<?> createRestaurant(RestSaveRequest rs ,Principal p)
	{
	
        return this.service.addRestaurant(rs,p);
	}
	
}
