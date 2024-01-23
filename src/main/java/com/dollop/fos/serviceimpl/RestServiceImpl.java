package com.dollop.fos.serviceimpl;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.RestAddress;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.entity.User;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.helper.FolderName;
import com.dollop.fos.reposatory.IRestAddressRepo;
import com.dollop.fos.reposatory.IRestaurantRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.requests.RestAddressRequest;
import com.dollop.fos.requests.RestSaveRequest;
import com.dollop.fos.service.IRestaurantService;
import com.dollop.fos.utility.IImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class RestServiceImpl implements IRestaurantService {

	@Autowired
	private IUserRepo urepo;
	
	@Autowired
	private IRestaurantRepo repo;
	
	@Autowired
	private IImageService iService;
	
	 @Autowired 
	 private ObjectMapper objectMapper;
	 
	@Override
	public ResponseEntity<?> addRestaurant(RestSaveRequest rest,Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Restaurant r = this.setRestData(rest,p);
		System.out.println(r);
		Restaurant r1 = this.repo.findByRestName(r.getRestName());
		if(r1!=null) 
		{
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else 
		{
		String imageName;	
			
		try {
			imageName = this.iService.uploadImage(rest.getImageName(), FolderName.RESTAURANT);
			r.setRestImageName(imageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
		this.repo.save(r);
		response.put(AppConstant.RESPONSE_MESSAGE,AppConstant.RESTAURANT_ADD_SUCCESS);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		
	}

	private Restaurant setRestData(RestSaveRequest rest,Principal p) {
		// TODO Auto-generated method stub
		Restaurant r = new Restaurant();
		String id=UUID.randomUUID().toString();
	    r.setRestId(id);
		r.setRestName(rest.getRestName());
		r.setRestCloseTime(rest.getRestCloseTime());
		r.setRestOpenTime(rest.getRestOpenTime());
	    r.setIsActive(false);
	    r.setRestDescription(rest.getRestDescription());
	    r.setCreatedAt(LocalDateTime.now());
	    r.setIsBlocked(AppConstant.UNBLOCK);
	    r.setIsApprove(AppConstant.UNVERIFIED);
	    r.setCurrentStatus(AppConstant.REST_CURR_CLOSE);
	    User owner = this.urepo.getUserByName(p.getName()).get();
		r.setOwner(owner);
		RestAddressRequest addressRequest;
		try {
			addressRequest = this.objectMapper.readValue(rest.getAddressrequest(), RestAddressRequest.class);
			r.setRestAddress(this.setDataInRestAddress(addressRequest,id));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return r;
	}

	

	private RestAddress setDataInRestAddress(RestAddressRequest request,String id) {
		// TODO Auto-generated method stub
		RestAddress address = new RestAddress();
		System.err.println(request.getCity());
		address.setRestAddressId(UUID.randomUUID().toString());
		address.setCity(request.getCity());
		address.setRestContect(request.getRestContect());
		address.setState(request.getState());
		address.setStreet(request.getStreet());
		address.setZipCode(request.getZipCode());
		address.setLatitude(request.getLatitude());
		address.setLongitude(request.getLongitude());
		return address;
	}

}
