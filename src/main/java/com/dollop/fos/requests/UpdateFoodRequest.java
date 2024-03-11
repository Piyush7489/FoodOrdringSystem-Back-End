package com.dollop.fos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateFoodRequest {

	private String foodId;
	private String foodDescription;
	private String foodName;
	private Long foodPrice;
	private String restCategoryid;
}
