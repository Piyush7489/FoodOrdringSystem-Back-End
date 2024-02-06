package com.dollop.fos.requests;


import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DeliveryBoyVerificationRequest {
	private String id;
	private MultipartFile identityCard;
	private String boyId;
    private String vehicleId;
	private String vehicleRequest;
	private MultipartFile licencePhoto;
	private MultipartFile vehicleImage;
}
