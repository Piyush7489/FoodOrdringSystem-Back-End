package com.dollop.fos.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.DeliveryBoyVerificationRequest;
import com.dollop.fos.service.IDeliveryBoyService;
@RestController
@RequestMapping("/api/v1/boy")
public class DeliveryBoyController {
	
	@Autowired
    private IDeliveryBoyService service;
	
	@PostMapping("/register")	
	public ResponseEntity<?> verificationOfDeliveryBoy( DeliveryBoyVerificationRequest d)
	{
	   return this.service.deliveryBoyRegistration(d);
	}

}
