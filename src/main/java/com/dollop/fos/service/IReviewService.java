package com.dollop.fos.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.ReviewRequest;

public interface IReviewService  {
	
	ResponseEntity<?> createReview(ReviewRequest review);
	ResponseEntity<?> getAllReview();
	ResponseEntity<?> getAllReviewOfRestaurant(String restId);
	ResponseEntity<?> deleteReviewByAdmin(String reviewId);
	ResponseEntity<?> deleteReviewByCustomer(String reviewId,Principal principal);

}
