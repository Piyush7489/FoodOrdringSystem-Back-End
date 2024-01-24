package com.dollop.fos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class WishListRespose {

	private String id;
	private String foodName;
	private String foodImage;
	private String foodDescription;
	private Long foodPrice;
	private Boolean foodisAvailable;
	private String foodCategoryName;
	
	
}
