package com.dollop.fos.requests;

import org.springframework.web.multipart.MultipartFile;

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
	private MultipartFile imageName;
	private Boolean isAvailable;
	private String foodCategoryName;
	private String restaurantId;
	private String categoryId;
	private String foodDescription;

}
