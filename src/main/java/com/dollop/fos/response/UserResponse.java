package com.dollop.fos.response;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.dollop.fos.entity.Address;
import com.dollop.fos.entity.Bill;
import com.dollop.fos.entity.Cart;
import com.dollop.fos.entity.Orders;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.entity.Review;
import com.dollop.fos.entity.UserRole;
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

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {

	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mob;
	private String tempAddress;
	private LocalDate createAt ;
	private Boolean isActive;
	private String profilePhoto;
	
}
