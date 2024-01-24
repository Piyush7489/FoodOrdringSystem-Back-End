package com.dollop.fos.document.restaurantapproval;

import com.dollop.fos.entity.Restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GstRegistration {
	@Id
	private String id;
	private String licenseNumber;
	private String licensePhoto;
	

}
