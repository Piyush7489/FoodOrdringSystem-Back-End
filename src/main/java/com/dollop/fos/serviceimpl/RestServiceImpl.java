package com.dollop.fos.serviceimpl;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.document.restaurantapproval.FssaiLiecence;
import com.dollop.fos.document.restaurantapproval.GstRegistration;
import com.dollop.fos.entity.GlobalCategory;
import com.dollop.fos.entity.RestAddress;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.entity.RestaurantCategory;
import com.dollop.fos.entity.User;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.helper.FolderName;
import com.dollop.fos.reposatory.IGlobalCaregoryRepo;
import com.dollop.fos.reposatory.IRestaurantCategoryRepo;
import com.dollop.fos.reposatory.IRestaurantRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.requests.FsseiLicenseRequest;
import com.dollop.fos.requests.GstRegistrationRequest;
import com.dollop.fos.requests.RestAddressRequest;
import com.dollop.fos.requests.RestCategoryRequest;
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
	 
	 @Autowired
	 private IGlobalCaregoryRepo gRepo;
	 
	 @Autowired
	 private IRestaurantCategoryRepo rcRepo;
	 
	 @Autowired
	 private ModelMapper mapper;
	 
	@Override
	public ResponseEntity<?> addRestaurant(RestSaveRequest rest,Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Restaurant r = this.setRestData(rest,p);
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
	   
		Restaurant save = this.repo.save(r);
		List<Object> abc = new ArrayList<>();
		List<String> restCategory = rest.getRestCategory();
		for(String rcr :restCategory )
		{
			System.err.println(rcr);
			GlobalCategory globalCategory = this.gRepo.findWithId(rcr);
			abc.add(globalCategory);
			System.err.println(globalCategory);
		}
		this.setDataInRestCategory(abc,save);
		response.put(AppConstant.RESPONSE_MESSAGE,AppConstant.RESTAURANT_ADD_SUCCESS);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		
	}

	private Restaurant setRestData(RestSaveRequest rest,Principal p) {
		// TODO Auto-generated method stub
//		System.err.println(rest.getRestCategory());
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
		FsseiLicenseRequest fsseiLicenseRequest;
		GstRegistrationRequest gstRegistrationRequest;
		RestCategoryRequest restCategoryRequest;
//		String restCategory = rest.getRestCategory();
		
		try {
			addressRequest = this.objectMapper.readValue(rest.getAddressrequest(), RestAddressRequest.class);
			fsseiLicenseRequest=this.objectMapper.readValue(rest.getFsseiLicenseRequest(), FsseiLicenseRequest.class);
			gstRegistrationRequest=this.objectMapper.readValue(rest.getGstRegistrationRequest(), GstRegistrationRequest.class);
//			restCategoryRequest=this.objectMapper.readValue(rest.getRestCategory(),RestCategoryRequest.class);
//			List<Object> abc = new ArrayList<>();
//			List<String> restCategory = rest.getRestCategory();
//			for(String rcr :restCategory )
//			{
//				System.err.println(rcr);
//				GlobalCategory globalCategory = this.gRepo.findWithId(rcr);
//				abc.add(globalCategory);
//				System.err.println(globalCategory);
//			}
			r.setFssaiLicense(this.setDataInFssei(fsseiLicenseRequest,rest.getFssaiLicensePhoto()));
			r.setGstRegistration(this.setDataInGst(gstRegistrationRequest,rest.getGstlicensePhoto()));
			r.setRestAddress(this.setDataInRestAddress(addressRequest,id));
//			r.setRestCategory(this.setDataInRestCategory(abc,id));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return r;
	}
	


	private List<RestaurantCategory> setDataInRestCategory(List<Object> abc,Restaurant rest) {
		// TODO Auto-generated method stub
		List<RestaurantCategory> list = new ArrayList<>();
		List<RestaurantCategory> changeObjTORestCat = this.changeObjTORestCat(abc);
		for(RestaurantCategory rc : changeObjTORestCat)
		{
			rc.setId(UUID.randomUUID().toString());
			rc.setRestaurant(rest);
			System.err.println(rc);
			list.add((RestaurantCategory) this.rcRepo.save(rc));
		}
			
		
		return list;
	}
	
	public List<RestaurantCategory> changeObjTORestCat(List<Object> obj)
	{
		List<RestaurantCategory> list = new ArrayList<>();
		for(Object ob : obj)
		{
			list.add(this.mapper.map(ob, RestaurantCategory.class));
		}
		return list;
	}

	private GstRegistration setDataInGst(GstRegistrationRequest gstRegistrationRequest, MultipartFile gstlicensePhoto) {
		// TODO Auto-generated method stub
		GstRegistration g = new GstRegistration();
		g.setId(UUID.randomUUID().toString());
		g.setLicenseNumber(gstRegistrationRequest.getLicenseNumber());
		String imageName;
		try {
			imageName = this.iService.uploadImage(gstlicensePhoto, FolderName.LICENSE);
			g.setGstlicensePhoto(imageName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return g;
	}

	private FssaiLiecence setDataInFssei(FsseiLicenseRequest fsseiLicenseRequest,MultipartFile license) {
		// TODO Auto-generated method stub
		FssaiLiecence f = new FssaiLiecence();
		f.setId(UUID.randomUUID().toString());
		f.setLicenseNumber(fsseiLicenseRequest.getLicenseNumber());
		String imagename;
		try {
			imagename = iService.uploadImage(license, FolderName.LICENSE);
			f.setFssaiLicensePhoto(imagename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		return f;
	}

	private RestAddress setDataInRestAddress(RestAddressRequest request,String id) {
		// TODO Auto-generated method stub
		RestAddress address = new RestAddress();
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
