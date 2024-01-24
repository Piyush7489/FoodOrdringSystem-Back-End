package com.dollop.fos.service;

public interface IEmailService {
	public Boolean sendEmail(String subject,String message, String sendTo);
}
