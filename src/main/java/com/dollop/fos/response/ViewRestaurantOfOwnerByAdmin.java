package com.dollop.fos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewRestaurantOfOwnerByAdmin {

	private String restId;
	private String restName;
	private String restCloseTime;
	private String restOpenTime;
	private Boolean isActive;
	private String currentStatus;
	private String restDescription;
	private LocalDateTime createdat;
	private String restImage;
	private String gstLicenceNo;
	private String gstLicencePhoto;
	private String fssaiLicenceNo;
	private String fssaiLicencePhoto;
    private String city;
    private String state;
    private String restContect;
    private String isApprove;
    
	
}
