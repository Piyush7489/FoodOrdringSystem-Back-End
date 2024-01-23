package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.Food;

public interface IFoodRepo extends JpaRepository<Food, String> {

}
