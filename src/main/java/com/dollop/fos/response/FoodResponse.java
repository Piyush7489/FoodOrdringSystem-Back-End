package com.dollop.fos.response;



import java.time.LocalDateTime;

import com.dollop.fos.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FoodResponse {

	@NonNull
	private String foodId;
	@NonNull
	private String foodName;
	@NonNull
	private Long foodPrice;
	@NonNull
	private LocalDateTime foodCreatedAt;
	@NonNull
	private String foodDescription;
	@NonNull
	private String imageName;
	@NonNull
	private Boolean isAvailable;
	@NonNull
	private String foodCategoryName;
	@NonNull
	private String globalCategoryId;
	@NonNull
	private String restName;
	@NonNull
	private String ownerName;
	
	
}
