package com.dollop.fos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
	@ToString
	public class RestaurantCategory {

		 @Id
		 private String id;
		 @ManyToOne
		 private Restaurant restaurant;
		 @ManyToOne
		 private GlobalCategory globalCategory;
	}


