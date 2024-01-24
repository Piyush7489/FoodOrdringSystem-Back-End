package com.dollop.fos.response;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {

	private Long foodId;
	private String foodName;
	private Long foodPrice;
	private String foodCreatedAt;
	private String foodDescription;
	private String imageName;
	private String type;
	private Boolean isAvailable;
	private String foodCategoryName;
	
}
