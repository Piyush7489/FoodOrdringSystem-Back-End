package com.dollop.fos.service;






import org.springframework.http.ResponseEntity;

import com.dollop.fos.entity.RegisterOtp;
import com.dollop.fos.requests.CheckOTPRequest;

public interface IRegisterOTPService {

	RegisterOtp saveOtp(RegisterOtp otp);
	RegisterOtp ifEmailExist(String email);
	RegisterOtp updateOTP(RegisterOtp ro, String Id);
	ResponseEntity<?> checkOTP(CheckOTPRequest cor);
}
