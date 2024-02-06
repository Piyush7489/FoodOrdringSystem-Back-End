package com.dollop.fos.serviceimpl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.document.deliveryboyverification.DeliveryBoyVerification;
import com.dollop.fos.document.deliveryboyverification.Vehicle;
import com.dollop.fos.entity.User;
import com.dollop.fos.helper.FolderName;
import com.dollop.fos.reposatory.IDeliveryboyVerificationRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.reposatory.IVehicleRepo;
import com.dollop.fos.requests.DeliveryBoyVerificationRequest;
import com.dollop.fos.requests.ViechelRequest;
import com.dollop.fos.service.IDeliveryBoyService;
import com.dollop.fos.utility.IImageService;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class DeliveryBoyVerificationImpl implements IDeliveryBoyService{

	@Autowired
	private IDeliveryboyVerificationRepo repo;
	@Autowired
	private IUserRepo userRepo;
	@Autowired 
	 private ObjectMapper objectMapper;
	@Autowired
	private IImageService iService;
	@Autowired
	private IVehicleRepo vRepo;
	
	@Override
	public ResponseEntity<?> deliveryBoyRegistration(DeliveryBoyVerificationRequest d) {
		// TODO Auto-generated method stub
	    System.err.println("1");
		DeliveryBoyVerification boyVerification  = setDataInBoyVerification(d);
		this.repo.save(boyVerification);
		return null;
	}
	private DeliveryBoyVerification setDataInBoyVerification(DeliveryBoyVerificationRequest d) {
		// TODO Auto-generated method stub
		System.err.println("2");
		DeliveryBoyVerification v = new DeliveryBoyVerification();
		System.err.println("3");
		User boy = this.userRepo.findById(d.getBoyId()).get();
		System.err.println("4");
		v.setBoy(boy);
		System.err.println("5");
		v.setId(UUID.randomUUID().toString());
		System.err.println("6");
		ViechelRequest viechelRequest;
		System.err.println("7"+d.getVehicleRequest());
		String identityCard;
		try {
			viechelRequest=this.objectMapper.readValue(d.getVehicleRequest(), ViechelRequest.class);
			System.err.println("8"+viechelRequest);
			identityCard = iService.uploadImage(d.getIdentityCard(),FolderName.IDENTITY_CARD);
			v.setVehicle(setDataInVehicle(viechelRequest,d.getLicencePhoto(),d.getVehicleImage()));
			v.setIdentityCard(identityCard);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v;
	}
	private Vehicle setDataInVehicle(ViechelRequest viechelRequest, MultipartFile licenceImage,
			MultipartFile vehicleImage) {
		// TODO Auto-generated method stub
		Vehicle v = new Vehicle();
		v.setId(UUID.randomUUID().toString());
		v.setLicenceNo(viechelRequest.getLicenceNo());
		v.setRegistrationNumber(viechelRequest.getRegistrationNumber());
		String licencePhoto=null;
		String vehiclePhoto=null;
		try {
			licencePhoto=this.iService.uploadImage(licenceImage, FolderName.LICENSE);
			vehiclePhoto=this.iService.uploadImage(vehicleImage, FolderName.VEHICLE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     v.setLicencePhoto(licencePhoto);
	     v.setVehicleImage(vehiclePhoto);
	     
		return this.vRepo.save(v);
	}

	
}
