package com.dollop.fos.reposatory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.fos.entity.GlobalCategory;
import com.dollop.fos.response.GlobalCategoryNameResponse;

public interface IGlobalCaregoryRepo extends JpaRepository<GlobalCategory, String> {

	@Query("SELECT g FROM GlobalCategory g WHERE g.catName=:catName")
	GlobalCategory findbyCatName(String catName);
	
	@Query("SELECT g FROM GlobalCategory g WHERE g.catId=:catId")
	GlobalCategory findWithId(String catId);

	  @Query("SELECT g FROM GlobalCategory g WHERE g.catName = :cName AND g.catId != :catId")
	    GlobalCategory findbyCatNameNotThisId(@Param("cName") String cName, @Param("catId") String catId);

//	@Query("SELECT g FROM GlobalCategory g WHERE g.catName=:catName AND g.catId!=catId")
//	GlobalCategory findbyCatNameNotThisId(@Param("catName")String catName,@Param("catId") String catId);
//	

	  
	  Page<GlobalCategory> findAll(Pageable pageable);
	  
	

	List<GlobalCategory> findByIsActiveIsTrue();
	
	 @Query("SELECT new com.dollop.fos.response.GlobalCategoryNameResponse(g.catId, g.catName) FROM RestaurantCategory rc JOIN GlobalCategory g ON rc.globalCategory.catId=g.catId  WHERE rc.restaurant.restId = :restaurantId")
	    List<GlobalCategoryNameResponse> findCatIdAndCatNameByRestaurantId(@Param("restaurantId") String restaurantId);
}
