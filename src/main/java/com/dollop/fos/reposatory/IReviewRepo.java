package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.Review;

public interface IReviewRepo extends JpaRepository<Review,String>{

}
