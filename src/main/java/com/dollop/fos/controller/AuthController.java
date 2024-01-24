package com.dollop.fos.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.payload.LoginRequest;
import com.dollop.fos.requests.SignupRequest;
import com.dollop.fos.service.IUserService;
import com.dollop.fos.utility.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private IUserService service;	
	
	 @Autowired
	 private AuthenticationManager authenticationmanger;
	
	 @Autowired
	 private JwtUtils jwtutil;
	 
	@PostMapping("/signup")
	public ResponseEntity<?> saveUser(@RequestBody SignupRequest request) {
		return this.service.createUser(request);
	}
 
	@PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody LoginRequest request)
	 {
		 authenticationmanger.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail().trim(), request.getPassword().trim()));
		 return jwtutil.generateToken(request.getEmail());

	 }
}
