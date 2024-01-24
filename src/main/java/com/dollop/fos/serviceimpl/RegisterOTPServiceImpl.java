package com.dollop.fos.serviceimpl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dollop.fos.customexceptions.OtpTimeExpireException;
import com.dollop.fos.customexceptions.ResourceFoundException;
import com.dollop.fos.customexceptions.ResourceNotFoundException;
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
	public Boolean checkOTP(CheckOTPRequest cor) {
		// TODO Auto-generated method stub
		Optional<RegisterOtp> optional = this.repo.findByEmail(cor.getEmail());
		RegisterOtp registerOTP = optional.get();
		System.out.println(cor.getOtp()+"F OTP"+"  ===  DOTP "+registerOTP.getOtp()+"X");
		if(optional.isPresent())
		{
			if(optional.get().getIsVerify())
			{
				throw new ResourceFoundException(AppConstant.DUPLICATE_EMAIL);
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
							return true;
						}
					else
					{
						System.out.println("CHECK OTP FALSE");
						return false;
					}
				}
				else
				{
					throw new OtpTimeExpireException(AppConstant.OTP_TIME_EXPIRE);
				}	
			}
		}
		throw new ResourceNotFoundException(AppConstant.NO_VALUE_PRESENT);
		
	}

}
