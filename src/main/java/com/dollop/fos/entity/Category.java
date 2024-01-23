package com.dollop.fos.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category {
	@Id
	private String catId;
	private String catName;
	private String catDescription;
	private Boolean isActive;
	@OneToMany(mappedBy = "category")
	private List<Food> listOfFood;
	@ManyToOne
	@JsonIgnoreProperties(value = "listOfCategory")
	private Restaurant restaurant;
}