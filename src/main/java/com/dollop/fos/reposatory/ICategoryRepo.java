package com.dollop.fos.reposatory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.Category;

public interface ICategoryRepo extends JpaRepository<Category, String> {

Category findBycatName(String name);
	
	public Category findCatnameByCatId(String id);
	
	@Query("SELECT c FROM Category c WHERE c.catId=:catId")
	public Category findByCatId(String catId);
	
	@Query("SELECT c FROM Category c WHERE  c.restaurant.restId=:restId")
	public List<Category> findCategoryByRestId(String restId);

	@Query("SELECT c FROM Category c WHERE c.catName=:cName AND c.restaurant.restId=:rId")
	Category findBycatNameAndRestId(String cName, String rId);

	@Query("SELECT c FROM Category c WHERE c.restaurant.restId in (SELECT r.restId FROM Restaurant r WHERE r.owner.userId=:uId)")
	List<Category> getAllCategoryOfOwnerRestaurants(String uId);
}





