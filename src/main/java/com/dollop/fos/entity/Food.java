package com.dollop.fos.entity;




import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long foodId;
	private String foodName;
	private Long foodPrice;
	private String foodCreatedAt;
	private String imageName;
	private Boolean isAvailable;
	private String foodCategoryName;
	private String foodDescription;
	@ManyToOne
	@JoinColumn(name = "restId")
	@JsonIgnoreProperties(value="listOfFood")
	private Restaurant restaurant;
	@ManyToOne
	@JoinColumn(name = "catId")
	@JsonIgnoreProperties(value = "listOfFood")
	private Category category;

}
