package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.Food;

public interface IFoodRepo extends JpaRepository<Food, String> {

	@Query("SELECT f FROM Food f WHERE f.foodId=:foodId")
	Food findFoodByFoodId(String foodId);

}
