package com.dollop.fos.requests;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserUpdateRequest {
	
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	private String mob;
	private String tempAddress;
//	private MultipartFile profilePhoto;

}
