package com.dollop.fos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class SignupRequest {

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mob;
	private String tempAddress;
	private String tempRole;
	

}
