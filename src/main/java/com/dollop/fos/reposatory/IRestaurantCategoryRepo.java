package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.RestaurantCategory;

public interface IRestaurantCategoryRepo extends JpaRepository<RestaurantCategory, String> {

//	@Query("SELECT rc FROM RestaurantCategory rc WHERE rc.id=:restcategoryId")
//	RestaurantCategory findByRestCatId(String restcategoryId);

	@Query("SELECT rc FROM RestaurantCategory rc WHERE rc.restaurant.restId=:restaurantId AND rc.globalCategory.catId=:globalCategoryId")
	RestaurantCategory findByRestCatId(String restaurantId, String globalCategoryId);

}
