package com.dollop.fos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalCategoryNameResponse {

	private String catId;
	private String catName;
}
