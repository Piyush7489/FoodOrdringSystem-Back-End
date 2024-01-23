package com.dollop.fos.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategorySaveRequest {

	
	private String catId;
	private String catName;
	private String catDescription;
	private Boolean isActive;
	private String restId;
	
}
