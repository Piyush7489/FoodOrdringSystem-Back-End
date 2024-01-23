package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;


import com.dollop.fos.requests.SignupRequest;



public interface IUserService {
	public ResponseEntity<?> createUser(SignupRequest user);
	public String getUserRole(String email); 
	
}
