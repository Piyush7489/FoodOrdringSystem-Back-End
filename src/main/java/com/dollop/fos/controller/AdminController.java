package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.AddFoodRequest;
import com.dollop.fos.requests.CategorySaveRequest;
import com.dollop.fos.requests.RestaurantRequest;
import com.dollop.fos.service.IAdminService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	private IAdminService service;

	@PutMapping("/RestaurantApprove/{restId}")
	public ResponseEntity<?> approveRestaurant(@PathVariable String restId) {
		return this.service.verifyRestaurant(restId);
	}

	@PutMapping("/RestaurantBlock/{restId}")
	public ResponseEntity<?> blockRestaurant(@PathVariable String restId) {
		return this.service.blockedRestaurant(restId);
	}

	@PutMapping("/RestaurantUnBlock/{restId}")
	public ResponseEntity<?> UnBlockRestaurant(@PathVariable String restId) {
		return this.service.unblockedRestaurant(restId);
	}

	@PostMapping("/all/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> getAllRestaurant(@PathVariable("pn") int pageNo, @PathVariable("ps") int pageSize,
			@PathVariable String sortBy, @RequestBody RestaurantRequest request) {
		return this.service.viewAllRestaurant(pageNo, pageSize, sortBy, request);
	}

	@PostMapping("/Verify/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> viewAllVerifiedRestaurant(@PathVariable("pn") int pageNo, @PathVariable("ps") int pageSize,
			@PathVariable String sortBy, @RequestBody RestaurantRequest request) {
		return this.service.viewAllVerifiedRestaurant(pageNo, pageSize, sortBy, request);
	}

	@PostMapping("/UnVerify/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> viewAllUnVerifiedRestaurant(@PathVariable("pn") int pageNo,
			@PathVariable("ps") int pageSize, @PathVariable String sortBy, @RequestBody RestaurantRequest request) {
		return this.service.viewAllUnVerifiedRestaurant(pageNo, pageSize, sortBy, request);
	}

	@PostMapping("/UnBlock/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> viewAllUnBlockRestaurant(@PathVariable("pn") int pageNo, @PathVariable("ps") int pageSize,
			@PathVariable String sortBy, @RequestBody RestaurantRequest request) {
		return this.service.viewAllUnBlockRestaurant(pageNo, pageSize, sortBy, request);
	}

	@PostMapping("/Block/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> viewAllBlockRestaurant(@PathVariable("pn") int pageNo, @PathVariable("ps") int pageSize,
			@PathVariable String sortBy, @RequestBody RestaurantRequest request) {
		return this.service.viewAllBlockRestaurant(pageNo, pageSize, sortBy, request);
	}

	@PostMapping("/AllFood/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> viewAllFood(@PathVariable("pn") int pageNo, @PathVariable("ps") int pageSize,
			@PathVariable String sortBy, @RequestBody AddFoodRequest request) {
		return this.service.viewAllFood(pageNo, pageSize, sortBy, request);
	}

	@PostMapping("/AllActiveFood/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> viewAllActiveFood(@PathVariable("pn") int pageNo, @PathVariable("ps") int pageSize,
			@PathVariable String sortBy, @RequestBody AddFoodRequest request) {
		return this.service.viewAllActiveFood(pageNo, pageSize, sortBy, request);
	}

	@PostMapping("/AllInActiveFood/{pn}/{ps}/{sortBy}")
	public ResponseEntity<?> viewAllInActiveFood(@PathVariable("pn") int pageNo, @PathVariable("ps") int pageSize,
			@PathVariable String sortBy, @RequestBody AddFoodRequest request) {
		return this.service.viewAllInActiveFood(pageNo, pageSize, sortBy, request);
	}
	
	

}
