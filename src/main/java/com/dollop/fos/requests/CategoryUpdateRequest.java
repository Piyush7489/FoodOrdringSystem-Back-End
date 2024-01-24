package com.dollop.fos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryUpdateRequest {
	
	private String 	catId;
	private String 	catName;
	private String 	catDescription;
	private Boolean isActive;
	private String 	restId;

}
