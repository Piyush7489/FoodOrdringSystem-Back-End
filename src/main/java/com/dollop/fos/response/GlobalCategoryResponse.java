package com.dollop.fos.response;

import com.dollop.fos.NonNull;

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
public class GlobalCategoryResponse {
	@NonNull
	private String catId;
	@NonNull
	private String catName;
	@NonNull
	private String catDescription;
	@NonNull
	private Boolean isActive;
	@NonNull
	private String catImage;
	

}
