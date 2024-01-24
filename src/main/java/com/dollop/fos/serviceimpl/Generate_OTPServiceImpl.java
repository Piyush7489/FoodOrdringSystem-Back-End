package com.dollop.fos.serviceimpl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.dollop.fos.service.IGenerate_OTPService;



@Service
public class Generate_OTPServiceImpl implements IGenerate_OTPService {

	@Override
	public Integer generate_OTP() {
		// TODO Auto-generated method stub
		Random random = new Random();
		int otp = random.nextInt(1000,9999);
		System.out.println("OTP : "+otp+" TEST : "+random.nextInt(9999));
		return otp;
	}

}
