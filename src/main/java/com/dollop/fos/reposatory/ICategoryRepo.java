package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dollop.fos.entity.Category;

@EnableJpaRepositories
public interface ICategoryRepo extends JpaRepository<Category, String> {
//
//Category findBycatName(String name);
//	
//	public Category findCatnameByCatId(String id);
//	
//	@Query("SELECT c FROM Category c WHERE c.catId=:catId")
//	public Category findByCatId(String catId);
//	
//	@Query("SELECT c FROM Category c WHERE  c.restaurant.restId=:restId")
//	public List<Category> findCategoryByRestId(String restId);
//
//	@Query("SELECT c FROM Category c WHERE c.catName=:cName AND c.restaurant.restId=:rId")
//	Category findBycatNameAndRestId(String cName, String rId);
//	
//	@Query("SELECT c.catId FROM Category c WHERE c.catId NOT IN (:catId) AND c.restaurant.restId = :restId AND c.catName = :catName")
//		List<Category> findCategoriesByParameters(
//		        @Param("catId") String catId,
//		        @Param("restId") String restId,
//		        @Param("catName") String catName
//		);
//
//
//	@Query("SELECT c FROM Category c WHERE c.restaurant.restId in (SELECT r.restId FROM Restaurant r WHERE r.owner.userId=:uId)")
//	List<Category> getAllCategoryOfOwnerRestaurants(String uId);
}





