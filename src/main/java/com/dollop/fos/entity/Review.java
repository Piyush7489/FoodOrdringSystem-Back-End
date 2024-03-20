package com.dollop.fos.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Review {
	@Id
	private String reviewId;
	private Integer rating;
	@ManyToOne
	@JoinColumn(name = "userIdfk")
	@JsonIgnoreProperties(value="listofReview")
	private User user;
	private String description;
	@ManyToOne
	@JoinColumn
	@JsonIgnoreProperties(value= {"listofReview"})
	private Restaurant rest;
	
	private LocalDateTime createAt;
}
