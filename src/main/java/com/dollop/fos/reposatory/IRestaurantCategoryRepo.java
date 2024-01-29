package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.RestaurantCategory;

public interface IRestaurantCategoryRepo extends JpaRepository<RestaurantCategory, String> {

}
