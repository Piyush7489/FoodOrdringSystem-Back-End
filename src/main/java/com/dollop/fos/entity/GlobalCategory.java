package com.dollop.fos.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
	@AllArgsConstructor
	@NoArgsConstructor
	@Setter
	@Getter
//	@ToString
	public class GlobalCategory {

		@Id
		private String catId;
		private String catName;
		private String catDescription;
		private Boolean isActive;
		@OneToMany(mappedBy = "globalCategory")
		@JsonIgnore
		private Set<RestaurantCategory> restCategory = new HashSet<>();
		
	}






