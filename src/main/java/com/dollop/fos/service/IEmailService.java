package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

public interface IEmailService {
	public ResponseEntity<?> sendEmail(String subject,String message, String sendTo);
}
