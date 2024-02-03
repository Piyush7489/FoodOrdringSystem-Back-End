package com.dollop.fos.entity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
	@Id
	private String userId;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private String mob;

	private String tempAddress;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = { "user" })
	private Set<UserRole> userRole = new HashSet<>();

	@OneToMany(mappedBy = "customer")
	@JsonIgnoreProperties(value = { "user" })
	private Set<Address> address = new HashSet<>();

	@OneToOne(mappedBy = "customer")
	@JsonIgnoreProperties(value = { "customer" })
	private Cart cart;

	@OneToOne(mappedBy = "customer")
	@JsonIgnoreProperties(value = { "customer" })
	private Bill bill;

	@OneToMany(mappedBy = "user")
	@JsonIgnoreProperties(value = { "user" })
	private List<Review> listofReview;

	@OneToMany(mappedBy = "user")
	@JsonIgnoreProperties(value = { "user" })
	private List<Orders> listOfOrders;

	@OneToMany(mappedBy = "owner")
	@JsonIgnoreProperties(value = { "owner" })
	private List<Restaurant> listofRestaurant;

	private LocalDate createAt;

	private LocalDate updateAt;

	private Boolean isActive;

	private String profilePhoto;
}
