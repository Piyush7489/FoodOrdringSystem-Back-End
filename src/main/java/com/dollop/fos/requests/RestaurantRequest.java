package com.dollop.fos.requests;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantRequest {

	private String restId;
	private String restName;
	private Boolean isActive;
	private String isApprove;
	private String isBlocked;
	
}
