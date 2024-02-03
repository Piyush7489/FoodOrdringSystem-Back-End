package com.dollop.fos.document.restaurantapproval;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private String gstlicensePhoto;
	

}
