package com.dollop.fos.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.EmailRequest;
import com.dollop.fos.service.IEmailService;



@RestController
@RequestMapping("/api/v1/emailAPI")
@CrossOrigin("*")
public class EmailController {
	
	@Autowired
	private IEmailService emailService;
	
	
	
	
	@PostMapping("/sentEmail")
	public ResponseEntity<?> sentEmail(@RequestBody EmailRequest emailRequest)
	{
		return this.emailService.sendEmail(emailRequest.getSubject(), emailRequest.getMessage(), emailRequest.getSendTo());	
	}
	@PostMapping("/forget-pass")
	public ResponseEntity<?> forgetPassEmail(@RequestBody EmailRequest emailRequest)
	{
		return this.emailService.forgetPassEmail(emailRequest.getSubject(), emailRequest.getMessage(), emailRequest.getSendTo());	
	}
}
