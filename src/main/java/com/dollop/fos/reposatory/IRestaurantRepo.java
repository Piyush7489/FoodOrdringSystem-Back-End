package com.dollop.fos.reposatory;

import java.util.List;
import java.util.Map;
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
	
	@Query("SELECT " +
	           "SUM(CASE WHEN r.isActive = true THEN 1 ELSE 0 END) AS activeCount, " +
	           "SUM(CASE WHEN r.isActive = false THEN 1 ELSE 0 END) AS inactiveCount, " +
	           "SUM(CASE WHEN r.isBlocked = 'BLOCK' THEN 1 ELSE 0 END) AS blockedCount, " +
	           "SUM(CASE WHEN r.isBlocked = 'UNBLOCK' THEN 1 ELSE 0 END) AS unblockedCount, " +
	           "SUM(CASE WHEN r.isApprove = 'Verified' THEN 1 ELSE 0 END) AS verifiedCount, " +
	           "SUM(CASE WHEN r.isApprove = 'Unverified' THEN 1 ELSE 0 END) AS unverifiedCount, " +
	           "COUNT(r) AS totalCount FROM Restaurant r")
	   public  Map<String, Long> getRestaurantStatusCounts();
	
	 

	
}
