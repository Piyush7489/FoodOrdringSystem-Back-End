package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.Restaurant;

public interface IRestaurantRepo extends JpaRepository<Restaurant, String> {

	public Restaurant findByRestName(String restName);
}
