package com.dollop.fos.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.payload.LoginRequest;
import com.dollop.fos.requests.ChangePasswordRequest;
import com.dollop.fos.requests.SignupRequest;
import com.dollop.fos.service.IUserService;
import com.dollop.fos.utility.JwtUtils;

@RestController
@CrossOrigin
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
		System.err.println("....");
		return this.service.createUser(request);
	}
 
	@PostMapping("/login")
	 public ResponseEntity<?> login(@RequestBody LoginRequest request)
	 {
		 authenticationmanger.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail().trim(), request.getPassword().trim()));
		 return jwtutil.generateToken(request.getEmail());

	 }
	@GetMapping("/current-user")
	public ResponseEntity<?> curretUser(Principal p)
	{
		return this.service.curentUser(p);
	}
	@GetMapping("/check/{email}")
	public ResponseEntity<?> Emailcheak(@PathVariable String email)
	{
	   return 	this.service.checkEmail(email);
	}
	@GetMapping("/new-pass/{email}/{pass}")
	public ResponseEntity<?> newPassForForgetPass(@PathVariable String email,@PathVariable String pass)
	{
      return this.service.newPassForForgetPass(email, pass);		
	}
	
	@PostMapping("/changePass")
	public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest cpr, Principal principal)
	{
		return this.service.changePassword(cpr, principal);
	}
	
	@PutMapping("/update-user/{userId}/{userUpdateReq}")
	public ResponseEntity<?> updateUser(@PathVariable String userId,
	                                     @PathVariable String userUpdateReq,
	                                     @RequestParam(value = "userImage", required = false) MultipartFile userImage) {
	    
	    return this.service.updateUser(userUpdateReq, userId, userImage);
	}
}
