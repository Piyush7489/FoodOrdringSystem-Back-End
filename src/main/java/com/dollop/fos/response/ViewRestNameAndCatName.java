package com.dollop.fos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewRestNameAndCatName {
	private String  restCatId;
	private String restName;
	private String catName;
}
