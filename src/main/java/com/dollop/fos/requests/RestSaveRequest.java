package com.dollop.fos.requests;



import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestSaveRequest {


    private String restId;
	private String restName;
	private String restCloseTime;
	private String restOpenTime;
	private Boolean isActive;
	private String currentStatus;
	private String restDescription;
	private LocalDateTime createdAt;
    private LocalDateTime updateAt;
	private Boolean isApprove;
	private String ownerId;
	private MultipartFile imageName;
	private String addressrequest;
	private String fssaiLicense;
	private String gstRegistration;
	

	
}
