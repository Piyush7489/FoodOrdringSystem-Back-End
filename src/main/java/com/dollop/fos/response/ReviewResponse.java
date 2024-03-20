package com.dollop.fos.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class ReviewResponse {

	private String reviewId;
	private Integer rating;
	private String description;
	private User_ReviewResponse user;
	private Rest_ReviewResponse rest;
	private LocalDateTime createAt;
}
