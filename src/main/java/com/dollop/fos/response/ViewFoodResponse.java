package com.dollop.fos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewFoodResponse {

	private String foodId;
	private String foodName;
	private Long foodPrice;
	private LocalDateTime foodCreatedAt;
	private String imageName;
	private Boolean isAvailable;
	private String foodDescription;
	private String foodCategoryName;
	private String foodRestaurantname;
	private String ownerId;
	private String restCategoryId;
}
