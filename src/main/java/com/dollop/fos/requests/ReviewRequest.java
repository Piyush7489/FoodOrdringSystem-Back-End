package com.dollop.fos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReviewRequest {
	
	private Integer rating;
	private String user;
	private String description;
	private String rest;

}
