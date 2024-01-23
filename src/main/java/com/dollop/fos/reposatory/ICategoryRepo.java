package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.Category;

public interface ICategoryRepo extends JpaRepository<Category, String> {

	@Query("SELECT c FROM Category c WHERE c.catName=:cName AND c.restaurant.restId=:rId")
	Category findBycatNameAndRestId(String cName, String rId);

}
