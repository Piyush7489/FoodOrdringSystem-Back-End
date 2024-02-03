package com.dollop.fos.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {

	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String mob;
	private String tempAddress;
	private LocalDate createAt ;
	private Boolean isActive;
	private String profilePhoto;
	private String userRole;
	
}
