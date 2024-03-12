package com.dollop.fos.response;

import java.time.LocalDate;

import com.dollop.fos.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerResponse {

	@NonNull
	private String userId;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	@NonNull
	private String email;
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
	
}
