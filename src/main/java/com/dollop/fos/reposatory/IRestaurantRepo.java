package com.dollop.fos.reposatory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.Restaurant;

public interface IRestaurantRepo extends JpaRepository<Restaurant, String> {

	public Restaurant findByRestName(String restName);
	
	@Query("SELECT r FROM Restaurant r WHERE r.restId=:restId")
	Restaurant findByRestId(String restId);

	@Query("SELECT r FROM Restaurant r WHERE r.isApprove=:verified")
	public List<Restaurant> findAllVerifiedRestaurant(String verified);

	
}
