package com.dollop.fos.document.deliveryboyverification;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Vehicle {

	@Id
	private String id;
	private String registrationNumber;
	private String licenceNo;
	private String licencePhoto;
	private String vehicleImage;
	@OneToOne(mappedBy = "vehicle")
	private DeliveryBoyVerification deliveryBoyVerification;
}
