package com.dollop.fos.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.requests.ChangePasswordRequest;
import com.dollop.fos.requests.SignupRequest;
import com.dollop.fos.requests.UserUpdateRequest;



public interface IUserService {
	public ResponseEntity<?> createUser(SignupRequest user);
	public ResponseEntity<?> updateUser(String updateRequest,String userId,MultipartFile profilePhoto);
	public String getUserRole(String email); 
	public ResponseEntity<?> curentUser(Principal p);
	public ResponseEntity<?> checkEmail(String email);
	public ResponseEntity<?> newPassForForgetPass(String email,String pass);
	public ResponseEntity<?> changePassword(ChangePasswordRequest cpr,Principal principal);
	
	
}
