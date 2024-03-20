package com.dollop.fos.reposatory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.fos.entity.Review;

public interface IReviewRepo extends JpaRepository<Review,String>{
	
	@Query("SELECT r FROM Review r WHERE r.rest.restId = :restId")
	List<Review> getReviewByRestaurantId(@Param("restId") String restId);

}
