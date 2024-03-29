package com.dollop.fos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class RegisterOtp {

	@Id
	private String id;
	private String email;
	private Integer otp;
	private Boolean isVerify;
	private LocalDateTime  createDateTime = LocalDateTime.now();
}
