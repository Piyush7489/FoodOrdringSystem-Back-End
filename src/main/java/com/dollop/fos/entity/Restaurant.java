package com.dollop.fos.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dollop.fos.document.restaurantapproval.FssaiLiecence;
import com.dollop.fos.document.restaurantapproval.GstRegistration;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity

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
	@JoinColumn(name = "rest_address_id")
	private RestAddress restAddress;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rest_fssai_ragistration_id")
	private FssaiLiecence fssaiLicense;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "rest_gst_ragistration_id")
	private GstRegistration gstRegistration;
	@ManyToOne
	@JoinColumn(name = "owner_id")
	@JsonIgnore
	private User owner;
	@OneToMany(mappedBy = "rest", cascade = CascadeType.ALL)
	@JsonIgnoreProperties(value = "rest")
	@JsonIgnore
	private List<Review> listOfReview = new ArrayList<>();
	@OneToMany(mappedBy = "restaurant")
	@JsonIgnore
	private List<RestaurantCategory> restCategory = new ArrayList<>();
}
