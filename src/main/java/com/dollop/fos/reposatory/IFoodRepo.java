package com.dollop.fos.reposatory;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.Food;
import com.dollop.fos.entity.RestaurantCategory;

public interface IFoodRepo extends JpaRepository<Food, String> {

	@Query("SELECT f FROM Food f WHERE f.foodId=:foodId")
	Food findFoodByFoodId(String foodId);

	@Query("SELECT f FROM Food f WHERE f.foodName=:foodname AND f.restCategory.id=:restCatId")
	Food findFoodByFoodName(String foodname,String restCatId);

	@Query("SELECT f FROM Food f WHERE f.foodName=:foodName AND f.restCategory.id=:restCategoryid AND f.foodId !=:foodId")
	Food findFoodByFoodNameButNotFoodId(String foodName, String restCategoryid, String foodId);

	

//    Page<Food> findByRestCategory(RestaurantCategory restCategory,Pageable pageable);
}