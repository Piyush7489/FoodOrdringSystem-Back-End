package com.dollop.fos.response;

import java.time.LocalDate;

import com.dollop.fos.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserResponse {

	@NonNull
	private String userId;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private String email;
	@NonNull
	private String password;
	@NonNull
	private String mob;
	@NonNull
	private String tempAddress;
	@NonNull
	private LocalDate createAt ;
	@NonNull
	private Boolean isActive;
	@NonNull
	private String profilePhoto;
	@NonNull
	private String userRole;
	
}
