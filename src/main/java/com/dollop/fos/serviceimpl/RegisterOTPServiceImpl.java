package com.dollop.fos.serviceimpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.customexceptions.ResourceFoundException;
import com.dollop.fos.entity.RegisterOtp;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IRegisterOTPRepo;
import com.dollop.fos.requests.CheckOTPRequest;
import com.dollop.fos.service.IRegisterOTPService;

@Service
public class RegisterOTPServiceImpl implements IRegisterOTPService {

	@Autowired
	private IRegisterOTPRepo repo;
	
	
	@Override
	public RegisterOtp saveOtp(RegisterOtp ro) {
		// TODO Auto-generated method stub
		Optional<RegisterOtp> optional = this.repo.findByEmail(ro.getEmail());
		if(optional.isPresent()) 
		{
			if(optional.get().getIsVerify()) 
			{
			   throw new ResourceFoundException(AppConstant.DUPLICATE_EMAIL);
			}
			RegisterOtp otp = this.repo.save(ro);
			return otp;
		}
		RegisterOtp otp = this.repo.save(ro);
		return otp;
	}


	@Override
	public RegisterOtp ifEmailExist(String email) {
		Optional<RegisterOtp> optional = this.repo.findByEmail(email);
		if(optional.isEmpty())
		{
			return null;
		}
		else {
			return optional.get();
		}
	}


	@Override
	public RegisterOtp updateOTP(RegisterOtp ro, String Id) {
		// TODO Auto-generated method stub
		ro.setId(Id);
		Optional<RegisterOtp> optional = this.repo.findByEmail(ro.getEmail());
		if(optional.get().getIsVerify())
		{
			throw new ResourceFoundException(AppConstant.DUPLICATE_EMAIL);
		}
		RegisterOtp otp = this.repo.save(ro);
		return otp;
		
	}


	@Override
	public ResponseEntity<?> checkOTP(CheckOTPRequest cor) {
		// TODO Auto-generated method stub
		Optional<RegisterOtp> optional = this.repo.findByEmail(cor.getEmail());
		Map<Object,Object> response = new HashMap<>();
		RegisterOtp registerOTP = optional.get();
		System.out.println(cor.getOtp()+"F OTP"+"  ===  DOTP "+registerOTP.getOtp()+"X");
		if(optional.isPresent())
		{
			if(optional.get().getIsVerify())
			{
				response.put(AppConstant.ERROR, AppConstant.DUPLICATE_EMAIL);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			else
			{
				Duration duration = Duration.between(registerOTP.getCreateDateTime(), LocalDateTime.now());
		        long minutesDifference = duration.toMinutes();
				if(minutesDifference < AppConstant.OTP_TIME_DIFERENCE)
				{
					if(registerOTP.getOtp().equals(cor.getOtp()))
						{
						RegisterOtp ro = new RegisterOtp(registerOTP.getId(), cor.getEmail(), cor.getOtp(), true,LocalDateTime.now());
							this.updateOTP(ro, registerOTP.getId());
							System.out.println("CHECK OTP TRUE");
							response.put(AppConstant.RESPONSE_MESSAGE,true);
							return ResponseEntity.status(HttpStatus.OK).body(response);
						}
					else
					{
						System.out.println("CHECK OTP FALSE");
						response.put(AppConstant.RESPONSE_MESSAGE,false);
						return ResponseEntity.status(HttpStatus.OK).body(response);
					}
				}
				else
				{
					response.put(AppConstant.ERROR, AppConstant.OTP_TIME_EXPIRE);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}	
			}
		}
		response.put(AppConstant.ERROR, AppConstant.NO_VALUE_PRESENT);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		
	}

}
