package com.dollop.fos.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@ToString
public class Restaurant {

	@Id
	private String restId;
	private String restName;
	private String restCloseTime;
	private String restOpenTime;
	private Boolean isActive;
	private String currentStatus;
	private String restDescription;
	private LocalDateTime createdAt;
    private LocalDateTime updateAt;
	private String isApprove;
	private String restImageName;
	private String isBlocked;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="rest_address_id")
	private RestAddress restAddress;
    @ManyToOne
    @JoinColumn(name="owner_id")
	private User owner;
	@OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value="restaurant")
	private List<Food> listOfFood;
	@OneToMany(mappedBy = "restaurant",cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value="restaurant")
	private List<Category> listOfCategory;
	@OneToMany(mappedBy = "rest",cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value="rest")
	private List<Review> listOfReview = new ArrayList<>();
	
	

}
