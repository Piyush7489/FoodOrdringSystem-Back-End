package com.dollop.fos.reposatory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.WishList;

public interface IWishListRepo extends JpaRepository<WishList, Long> {



	@Query("SELECT w FROM WishList w WHERE w.costomer.userId=:userId AND w.food.foodId=:foodId  ")
	List<WishList> findByUserIdAndFoodId(String userId, String foodId);

	@Query("SELECT w FROM WishList w WHERE w.costomer.userId=:userId")
	List<WishList> findWishListByUserId(String userId);

}
