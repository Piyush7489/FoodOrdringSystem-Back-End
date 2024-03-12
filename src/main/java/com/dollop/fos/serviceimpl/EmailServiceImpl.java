package com.dollop.fos.serviceimpl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.RegisterOtp;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IRegisterOTPRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.service.IEmailService;
import com.dollop.fos.service.IGenerate_OTPService;
import com.dollop.fos.service.IRegisterOTPService;
@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	private IGenerate_OTPService otp_service;
	
	@Autowired
	private IRegisterOTPService roService;
	
	@Autowired
	private IRegisterOTPRepo otpRepo;
	
	@Autowired
	private IUserRepo userRepo;
	

	@Override
	public ResponseEntity<?> forgetPassEmail(String subject, String message, String sendTo) {
		// TODO Auto-generated method stub
		System.err.println("oooo");
		 Boolean flag = false;
		 System.err.println(sendTo);
	      Optional<RegisterOtp> optional = otpRepo.findByEmail(sendTo);	
	      Map<String, Object> response = new HashMap<>();
	      if(optional.isEmpty() )
			{
			  response.put(AppConstant.ERROR, AppConstant.EMAIL_INVALID);
			  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} 
			 int otp = otp_service.generate_OTP();
			 String from = "kushvanshipp@gmail.com";
			 String host = "smtp.gmail.com";
			 Properties properties = System.getProperties();
			 System.out.println("PROPERTIES : "+properties);
			 properties.put("mail.smtp.host", host);
			 properties.put("mail.smtp.port", "465");
			 properties.put("mail.smtp.ssl.enable", "true");
			 properties.put("mail.smtp.auth", "true");
			 Session session = Session.getInstance(properties, new Authenticator() {
				 @Override
				 protected PasswordAuthentication getPasswordAuthentication() {
					 return new PasswordAuthentication(from, "jfrpgvpcxgeoqcph");
				 }
			 });
			 session.setDebug(true);
			 MimeMessage m = new MimeMessage(session);
			 try {
					
//				 FROM EMAIL
				 m.setFrom(from);
				 
//				 ADDING RECIPIENT TO MESSAGE
				 m.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
				 
//				 ADDING SUBJECT TO MESSAGE
				 m.setSubject(subject);
				 
//				 ADDING TEXT TO MESSAGE
				 message = "Dear [Customer],\r\n"
				 		+ "\r\n"
				 		+ "I hope this message finds you well.\r\n"
				 		+ "\r\n"
				 		+ "As part of our security protocol and to ensure the integrity of our systems, we require a one-time password (OTP) for administrative access. This OTP will be used to validate and authenticate the intended administrative tasks.\r\n"
				 		+ "\r\n"
				 		+ "Please find below the requested OTP details:\r\n"
				 		+ "\r\n"
				 		+ "OTP: [Insert One-Time Password]\r\n"+otp
				 		+ "\r\n"
				 		+ "Kindly utilize this OTP solely for the designated administrative purposes. As an added security measure, please refrain from sharing this OTP with anyone and ensure its usage is within the specified context.\r\n"
				 		+ "\r\n"
				 		+ "Should you encounter any issues or require further assistance, please do not hesitate to reach out to the IT support team at [IT Support Contact Information].\r\n"
				 		+ "\r\n"
				 		+ "Thank you for your cooperation in maintaining our system's security.\r\n"
				 		+ "\r\n"
				 		+ "Best regards,\r\n"
				 		+ "\r\n"
				 		+ "[Dollopian Foodie's]\r\n"
				 		+ "[7489727319]";
				 m.setText(message);
				 Transport.send(m);
				 RegisterOtp rOTP = new RegisterOtp();
				 System.out.println("MASSAGE SENT SUCCESS..............");
				 RegisterOtp ifEmailExist = this.roService.ifEmailExist(sendTo);
				 System.err.println(ifEmailExist+".."+sendTo);
				 if(Objects.nonNull(ifEmailExist))
				 {
					 System.err.println("inside if");
					 rOTP.setEmail(sendTo);
					 rOTP.setIsVerify(false);
					 rOTP.setOtp(otp);
					 rOTP.setCreateDateTime(LocalDateTime.now());
					 RegisterOtp updateOTP = this.roService.updateOTPForForget(rOTP, ifEmailExist.getId());
					 System.out.println(updateOTP);

					 flag=true;

				 }else
				 {
					 rOTP.setId(UUID.randomUUID().toString());
					 rOTP.setEmail(sendTo);
					 rOTP.setIsVerify(false);
					 rOTP.setOtp(otp);
					 RegisterOtp saveOTP = this.roService.saveOtp(rOTP);
					 System.out.println(saveOTP);
					 flag=true;
				 }
				 response.put(flag?AppConstant.RESPONSE_MESSAGE:AppConstant.ERROR, flag?AppConstant.OTP_SEND_SUCCESSFULLY:AppConstant.SOMETHING_WENT_WRONG);
				 return ResponseEntity.status(flag?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(response);
		}
			 catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					response.put(AppConstant.ERROR, AppConstant.INCARRECT_EMAIL_EXCEPTION);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			 }
		}
		
	
	
	
	
	@Override
	public ResponseEntity<?> sendEmail(String subject, String message, String sendTo) {
      Boolean flag = false;
      Optional<RegisterOtp> optional = otpRepo.findByEmail(sendTo);	
      Map<String, Object> response = new HashMap<>();
      if(optional.isPresent() )
		{
			if(optional.get().getIsVerify())
			{
				response.put(AppConstant.ERROR, AppConstant.EMAIL_ALREADY_REGISTERED);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} 
      
      
		int otp = otp_service.generate_OTP();
//		FROM
		String from = "kushvanshipp@gmail.com";
		
//		VARIABLE FOR GMAIL
		 String host = "smtp.gmail.com";
		 
//		 GET THE SYSTEM PROPERTIES
		 Properties properties = System.getProperties();
		 System.out.println("PROPERTIES : "+properties);
		 
//		 SETTING IMPORTANT INFORMATION TO PROPERTIES OBJECT
		 
//		 HOST SET
		 properties.put("mail.smtp.host", host);
		 properties.put("mail.smtp.port", "465");
		 properties.put("mail.smtp.ssl.enable", "true");
		 properties.put("mail.smtp.auth", "true");
		 
		 
//		 STEP 1 : TO GET SESSION OBJECT...
		 Session session = Session.getInstance(properties, new Authenticator() {
			 @Override
			 protected PasswordAuthentication getPasswordAuthentication() {
				 return new PasswordAuthentication(from, "jfrpgvpcxgeoqcph");
			 }
		 });
		 
		 
		 session.setDebug(true);
		 
//		 STEP 2 : COMPOSE THE MASSAGE [TEXT,MULTI MEDIA]
		 MimeMessage m = new MimeMessage(session);
		 
		 try {
			
//			 FROM EMAIL
			 m.setFrom(from);
			 
//			 ADDING RECIPIENT TO MESSAGE
			 m.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
			 
//			 ADDING SUBJECT TO MESSAGE
			 m.setSubject(subject);
			 
//			 ADDING TEXT TO MESSAGE
			 message = "Dear [Customer],\r\n"
			 		+ "\r\n"
			 		+ "I hope this message finds you well.\r\n"
			 		+ "\r\n"
			 		+ "As part of our security protocol and to ensure the integrity of our systems, we require a one-time password (OTP) for administrative access. This OTP will be used to validate and authenticate the intended administrative tasks.\r\n"
			 		+ "\r\n"
			 		+ "Please find below the requested OTP details:\r\n"
			 		+ "\r\n"
			 		+ "OTP: [Insert One-Time Password]\r\n"+otp
			 		+ "\r\n"
			 		+ "Kindly utilize this OTP solely for the designated administrative purposes. As an added security measure, please refrain from sharing this OTP with anyone and ensure its usage is within the specified context.\r\n"
			 		+ "\r\n"
			 		+ "Should you encounter any issues or require further assistance, please do not hesitate to reach out to the IT support team at [IT Support Contact Information].\r\n"
			 		+ "\r\n"
			 		+ "Thank you for your cooperation in maintaining our system's security.\r\n"
			 		+ "\r\n"
			 		+ "Best regards,\r\n"
			 		+ "\r\n"
			 		+ "[Dollopian Foodie's]\r\n"
			 		+ "[7489727319]";
					 
					 
					 
//			 m.setText(message+" "+otp_service.generate_OTP() );
			 m.setText(message);
			 
//			 SEND
			 
			 
			 
//			 STEP 3 : SEND MESSAGE USING TRANPORT CLASS
			 Transport.send(m);
			 
			 RegisterOtp rOTP = new RegisterOtp();
			 System.out.println("MASSAGE SENT SUCCESS..............");
			 RegisterOtp ifEmailExist = this.roService.ifEmailExist(sendTo);
			 System.err.println(ifEmailExist+".."+sendTo);
			 if(Objects.nonNull(ifEmailExist))
			 {
				 System.err.println("inside if");
				 rOTP.setEmail(sendTo);
				 rOTP.setIsVerify(false);
				 rOTP.setOtp(otp);
				 rOTP.setCreateDateTime(LocalDateTime.now());
				 RegisterOtp updateOTP = this.roService.updateOTP(rOTP, ifEmailExist.getId());
				 System.out.println(updateOTP);

				 flag=true;

			 }else
			 {
				 rOTP.setId(UUID.randomUUID().toString());
				 rOTP.setEmail(sendTo);
				 rOTP.setIsVerify(false);
				 rOTP.setOtp(otp);
				 RegisterOtp saveOTP = this.roService.saveOtp(rOTP);
				 System.out.println(saveOTP);
				 flag=true;
			 }
			 response.put(flag?AppConstant.RESPONSE_MESSAGE:AppConstant.ERROR, flag?AppConstant.OTP_SEND_SUCCESSFULLY:AppConstant.SOMETHING_WENT_WRONG);
			 return ResponseEntity.status(flag?HttpStatus.OK:HttpStatus.BAD_REQUEST).body(response);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.put(AppConstant.ERROR, AppConstant.INCARRECT_EMAIL_EXCEPTION);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		 
	}




}
