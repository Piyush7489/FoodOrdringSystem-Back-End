package com.dollop.fos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
}
