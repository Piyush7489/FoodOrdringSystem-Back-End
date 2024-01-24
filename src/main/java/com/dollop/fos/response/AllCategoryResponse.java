package com.dollop.fos.response;

import com.dollop.fos.requests.RestaurantRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AllCategoryResponse {
	
	private String catId;
	private String catName;
	private String catDescription;
	private Boolean isActive;
	private RestaurantRequest restaurant;
	private String ownerEmail;

}
