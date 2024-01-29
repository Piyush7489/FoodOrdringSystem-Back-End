package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dollop.fos.entity.GlobalCategory;

public interface IGlobalCaregoryRepo extends JpaRepository<GlobalCategory, String> {

	@Query("SELECT g FROM GlobalCategory g WHERE g.catName=:catName")
	GlobalCategory findbyCatName(String catName);
	
	@Query("SELECT g FROM GlobalCategory g WHERE g.catId=:catId")
	GlobalCategory findWithId(String catId);
	

}
