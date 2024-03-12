package com.dollop.fos.reposatory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.Restaurant;

public interface IRestaurantRepo extends JpaRepository<Restaurant, String> {

	public Restaurant findByRestName(String restName);
	
	@Query("SELECT r FROM Restaurant r WHERE r.restId=:restId")
	Restaurant findByRestId(String restId);

	@Query("SELECT r FROM Restaurant r WHERE r.isApprove=:verified")
	public List<Restaurant> findAllVerifiedRestaurant(String verified);

	@Query("SELECT r FROM Restaurant r WHERE r.owner.userId=:ownerId AND r.isApprove=:verified")
	public List<Restaurant> getApproveRestaurantByOwnerId(String ownerId,String verified);

	@Query("SELECT r FROM Restaurant r WHERE r.owner.userId=:ownerId")
	public List<Restaurant> getRestaurantByOwnerId(String ownerId);
	
	Optional<Restaurant> findByRestNameAndRestIdNot(String restName, String restId);

	
}
