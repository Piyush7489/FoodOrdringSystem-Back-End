package com.dollop.fos.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@ToString
public class Category {
	@Id
	private String catId;
	private String catName;
	private String catDescription;
	private Boolean isActive;
	@OneToMany(mappedBy = "category")
	private List<Food> listOfFood;
//	@ManyToOne
//	@JsonIgnoreProperties(value = "listOfCategory")
//	private Restaurant restaurant;
	@Override
	public String toString() {
		return "Category [catId=" + catId + ", catName=" + catName + ", catDescription=" + catDescription
				+ ", isActive=" + isActive ;
	}
	
	
}