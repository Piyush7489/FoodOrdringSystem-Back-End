package com.dollop.fos.serviceimpl;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.entity.Review;
import com.dollop.fos.entity.User;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IRestaurantRepo;
import com.dollop.fos.reposatory.IReviewRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.requests.ReviewRequest;
import com.dollop.fos.response.Rest_ReviewResponse;
import com.dollop.fos.response.ReviewResponse;
import com.dollop.fos.response.User_ReviewResponse;
import com.dollop.fos.service.IReviewService;

@Service
public class ReviewServiceImpl implements IReviewService{
	
	@Autowired
	private IReviewRepo repo;
	
	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	private IRestaurantRepo restRepo;

	@Override
	public ResponseEntity<?> createReview(ReviewRequest review) {
		
		Map<String,Object> response = new HashMap<>();
		Optional<User> optional = this.userRepo.findById(review.getUser());
		Restaurant restaurant = this.restRepo.findByRestId(review.getRest());
		if(optional.isPresent())
		{
			if(Objects.nonNull(restaurant))
			{
				Review reqToReview = this.reviewReqToReview(review);
				reqToReview.setRest(restaurant);
				reqToReview.setUser(optional.get());
				reqToReview.setReviewId(UUID.randomUUID().toString());
				reqToReview.setCreateAt(LocalDateTime.now());
				System.err.println(reqToReview.toString());
				Review save = this.repo.save(reqToReview);
				response.put(AppConstant.DATA,this.reviewToResponse(save) );
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
			else {
				response.put(AppConstant.ERROR, AppConstant.RESTAURANT_NOT_FOUND);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}else {
			response.put(AppConstant.ERROR, AppConstant.USER_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	
//	REVIEW REQUEST TO REVIEW
	public Review reviewReqToReview(ReviewRequest req)
	{
		Review review = new Review();
		review.setDescription(req.getDescription());
		review.setRating(req.getRating());
		return review;
	}
	
//	REVIEW TO REVIEW RESPONSE
	public ReviewResponse reviewToResponse(Review review)
	{
		ReviewResponse reviewResponse = new ReviewResponse();
		reviewResponse.setDescription(review.getDescription());
		reviewResponse.setRating(review.getRating());
		reviewResponse.setReviewId(review.getReviewId());
		reviewResponse.setUser(this.userToUser_ReviewResponse(review.getUser()));
		reviewResponse.setRest(this.restToRest_ReviewResponse(review.getRest()));
		reviewResponse.setCreateAt(review.getCreateAt());
		return reviewResponse;
	}
	
	
//	USER TO USER REVIEW RESPONSE
	public User_ReviewResponse userToUser_ReviewResponse(User u)
	{
		User_ReviewResponse userResponse = new User_ReviewResponse();
		userResponse.setUserId(u.getUserId());
		userResponse.setFirstName(u.getFirstName());
		userResponse.setLastName(u.getLastName());
		userResponse.setEmail(u.getEmail());
		userResponse.setProfilePhoto(u.getProfilePhoto());
		return userResponse;
	}
	
	
//	RESTAURANT TO RESTAURANT REVIEW RESPONSE
	public Rest_ReviewResponse restToRest_ReviewResponse(Restaurant rest)
	{
		Rest_ReviewResponse response = new Rest_ReviewResponse();
		response.setRestId(rest.getRestId());
		response.setRestImageName(rest.getRestImageName());
		response.setRestName(rest.getRestName());
		return response;
	}


	@Override
	public ResponseEntity<?> getAllReview() {
		Map<String,Object> response = new HashMap<>();
		List<Review> findAll = this.repo.findAll();
		if(Objects.nonNull(findAll))
		{
			List<ReviewResponse> list = findAll.stream().map(this::reviewToResponse).collect(Collectors.toList());
			response.put(AppConstant.DATA, list);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			response.put(AppConstant.ERROR, AppConstant.REVIEW_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}


	@Override
	public ResponseEntity<?> getAllReviewOfRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		List<Review> reviewByRestaurantId = this.repo.getReviewByRestaurantId(restId);
		if(Objects.nonNull(reviewByRestaurantId))
		{
			List<ReviewResponse> list = reviewByRestaurantId.stream().map(this::reviewToResponse).collect(Collectors.toList());
			response.put(AppConstant.DATA, list);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			response.put(AppConstant.ERROR, AppConstant.REVIEW_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}


	@Override
	public ResponseEntity<?> deleteReviewByAdmin(String reviewId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Optional<Review> optional = this.repo.findById(reviewId);
		if(optional.isPresent())
		{
			this.repo.delete(optional.get());
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.REVIEW_DELETED);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			response.put(AppConstant.ERROR, AppConstant.REVIEW_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}


	@Override
	public ResponseEntity<?> deleteReviewByCustomer(String reviewId,Principal principal) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Optional<Review> optional = this.repo.findById(reviewId);
		if(optional.isPresent()) {
			if(optional.get().getUser().getEmail().equals(principal.getName()))
			{
				this.repo.delete(optional.get());
				response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.REVIEW_DELETED);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}else {
				response.put(AppConstant.ERROR, AppConstant.SOMEONES_REVIEW);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
		else {
			response.put(AppConstant.ERROR, AppConstant.REVIEW_NOT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
