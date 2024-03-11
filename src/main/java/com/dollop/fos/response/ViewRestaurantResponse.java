package com.dollop.fos.response;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ViewRestaurantResponse {

	private String restId;
	
	private String restName;
	
	private String restCloseTime;
	
	private String restOpenTime;
	
	private Boolean isActive;
	
	private String currentStatus;
	
	private String restDescription;

	private String isApprove;
	
	private String isBlocked;
	
	private String  restImage;
	
	private String createdAt;

	private String restAddressId;
	
	private String street;
	
    private String city;
	
    private String state;
	
    private String latitude;
	
    private String longitude;

    private String zipCode;
	
    private String restContect;
    
    private MultipartFile imageName;
	private String fssaiLicenceNo;
	private String gstLicenceNo;
	
	
}
