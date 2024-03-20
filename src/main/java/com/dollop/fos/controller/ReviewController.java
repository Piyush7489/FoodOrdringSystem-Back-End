package com.dollop.fos.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.ReviewRequest;
import com.dollop.fos.service.IReviewService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

	@Autowired
	private IReviewService service;
	
	@PostMapping("/create-review")
	public ResponseEntity<?> createReview(@RequestBody ReviewRequest reviewReq)
	{
		return this.service.createReview(reviewReq);
	}
	
	@GetMapping("/list-of-review")
	public ResponseEntity<?> getAllReview()
	{
		return this.service.getAllReview();
	}
	
	@GetMapping("/list-of-review-by-restId/{restId}")
	public ResponseEntity<?> getAllReviewOfRestaurant(@PathVariable String restId)
	{
		return this.service.getAllReviewOfRestaurant(restId);
	}
	
	@DeleteMapping("/delete-review-by-admin/{reviewId}")
	public ResponseEntity<?> deleteReviewByAdmin(@PathVariable String reviewId)
	{
		return this.service.deleteReviewByAdmin(reviewId);
	}
	
	@DeleteMapping("/delete-review-by-customer/{reviewId}")
	public ResponseEntity<?> deleteReviewByCustomer(Principal p,@PathVariable String reviewId)
	{
		return this.service.deleteReviewByCustomer(reviewId, p);
	}
}
