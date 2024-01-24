package com.dollop.fos.controller;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.entity.RegisterOtp;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IRegisterOTPRepo;
import com.dollop.fos.requests.EmailRequest;
import com.dollop.fos.service.IEmailService;



@RestController
@RequestMapping("/api/v1/emailAPI")
public class EmailController {
	
	@Autowired
	private IEmailService emailService;
	
	
	@Autowired
	private IRegisterOTPRepo otpRepo;
	
	@PostMapping("/sentEmail")
	public ResponseEntity<?> sentEmail(@RequestBody EmailRequest emailRequest)
	{
			
		System.err.println("controller");
		Optional<RegisterOtp> optional = otpRepo.findByEmail(emailRequest.getSendTo());	

		if(optional.isPresent())
		{
			if(optional.get().getIsVerify())
			{
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AppConstant.EMAIL_ALREADY_REGISTERED);
			}
		} 
		Boolean entity = this.emailService.sendEmail(emailRequest.getSubject(), emailRequest.getMessage(), emailRequest.getSendTo());
		Map<String, Object> response = new HashMap<>();
		 if(entity)
		 {
			 System.out.println("inside if");
			 response.put("data", "Email Sent Sucessfully....");
			 return ResponseEntity.ok(response);
		 }
		 else
		 { System.out.println("inside else");
		 response.put("data", "Something went wrong....");
			 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		 }
		
	}
	
}
