package com.dollop.fos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class AddFoodRequest {
	
	private String foodId;
	private String foodName;
	private Long foodPrice;
	private String foodCreatedAt;
	private Boolean isAvailable;
	private String restcategoryId;
	private String foodDescription;
	private String globalCategoryId;
	private String restaurantId;

}
