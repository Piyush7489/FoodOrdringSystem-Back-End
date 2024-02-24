package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.CheckOTPRequest;
import com.dollop.fos.service.IRegisterOTPService;


@RestController
@RequestMapping("/api/v1/otp")
@CrossOrigin("*")
public class RegisterOTPController {

	@Autowired
	private IRegisterOTPService otpService;
	
	@PostMapping("/checkOTP")
	private ResponseEntity<?> checkOTP(@RequestBody CheckOTPRequest cor)
	{
		System.err.println(cor);
		
		return this.otpService.checkOTP(cor);
		
	}
}
