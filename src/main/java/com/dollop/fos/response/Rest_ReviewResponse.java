package com.dollop.fos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Rest_ReviewResponse {
	
	private String restId;
	private String restName;
	private String restImageName;

}
