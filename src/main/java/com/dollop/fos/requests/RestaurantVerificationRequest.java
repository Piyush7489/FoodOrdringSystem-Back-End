package com.dollop.fos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantVerificationRequest {

	
	private String restid;
	private String restImage;
	private String gstLicencePhoto;
	private String FssaiLicencePhoto;
    private String gstlicenseNo;
    private String FssaiLicenceNo;
    private String restname;
}
