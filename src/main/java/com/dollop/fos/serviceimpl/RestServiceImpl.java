package com.dollop.fos.serviceimpl;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.dollop.fos.requests.RestSaveRequest;
import com.dollop.fos.response.RestNameResponse;
import com.dollop.fos.response.ViewRestaurantOfOwnerByAdmin;
import com.dollop.fos.response.ViewRestaurantResponse;
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

		
		
		Restaurant r1 = this.repo.findByRestName(rest.getRestName());
		if(r1!=null) 
		{
			response.put(AppConstant.ERROR, AppConstant.RESTAURANT_FOUND);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}else 
		{
		Restaurant r = this.setRestData(rest,p);
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
			GlobalCategory globalCategory = this.gRepo.findWithId(rcr);
			abc.add(globalCategory);
		}
		this.setDataInRestCategory(abc,save);
		response.put(AppConstant.RESPONSE_MESSAGE,AppConstant.RESTAURANT_ADD_SUCCESS);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		
	}

	private Restaurant setRestData(RestSaveRequest rest,Principal p) {
		System.err.println("2");
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
		try {
			addressRequest = this.objectMapper.readValue(rest.getAddressrequest(), RestAddressRequest.class);
			fsseiLicenseRequest=this.objectMapper.readValue(rest.getFsseiLicenseRequest(), FsseiLicenseRequest.class);
			gstRegistrationRequest=this.objectMapper.readValue(rest.getGstRegistrationRequest(), GstRegistrationRequest.class);
			r.setRestAddress(this.setDataInRestAddress(addressRequest,id));
			r.setFssaiLicense(this.setDataInFssei(fsseiLicenseRequest,rest.getFssaiLicensePhoto()));
			r.setGstRegistration(this.setDataInGst(gstRegistrationRequest,rest.getGstlicensePhoto()));
			
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
		System.err.println("5");
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
		System.err.println("4");
		f.setId(UUID.randomUUID().toString());
		f.setLicenseNumber(fsseiLicenseRequest.getLicenseNumber());
		String imagename;
		try {
			System.err.println("image"+license);
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
		System.err.println("3");
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

	@Override
	public ResponseEntity<?> changeStatus(String id, boolean status) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Restaurant r = this.repo.findByRestId(id);
		r.setRestId(id);
		r.setIsActive(status);
		this.repo.save(r);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.RESTAURANT_UPDATE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<?> getDataofRestaurant(int page, int size) {
		// TODO Auto-generated method stub
		 Map<String,Object> response = new HashMap<>();
		 Pageable pageable = PageRequest.of(page, size);
		 Page <Restaurant>restaurant= repo.findAll(pageable);
		 List<ViewRestaurantResponse> viewRestaurant = restaurant.getContent().stream().map(this::restToViewRestResponse) .collect(Collectors.toList());
		 Page page1 = new PageImpl<>(viewRestaurant,pageable,restaurant.getTotalElements());
		 response.put(AppConstant.RESPONSE_MESSAGE, page1);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	private ViewRestaurantResponse restToViewRestResponse(Restaurant r) {
		ViewRestaurantResponse v = new ViewRestaurantResponse();
		v.setRestId(r.getRestId());
		v.setRestName(r.getRestName());
		v.setCurrentStatus(r.getCurrentStatus());
		v.setIsActive(r.getIsActive());
		v.setIsBlocked(r.getIsBlocked());
		v.setIsApprove(r.getIsApprove());
		v.setRestCloseTime(r.getRestCloseTime());
		v.setRestOpenTime(r.getRestOpenTime());
		v.setRestDescription(r.getRestDescription());
		return v;
	}

	@Override
	public ResponseEntity<?> editRestaurant(ViewRestaurantResponse rest, Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		
		Restaurant r1 = this.repo.findByRestId(rest.getRestId());
		if(r1.getIsApprove().equals(AppConstant.UNVERIFIED)) 
		{
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.WAIT_FOR_APPROVAL_RESTAURANT);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
//		else if(r1.getIsBlocked().equals(AppConstant.UNBLOCK)) 
//		{
//			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.WAIT_FOR_APPROVAL_RESTAURANT);
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//		}
		Optional<Restaurant> restaurant = this.repo.findByRestNameAndRestIdNot(rest.getRestName(), rest.getRestId());
	    if(restaurant.isEmpty()) 
	    {
	    	Restaurant r = this.repo.findByRestId(rest.getRestId());
	    	System.err.println(r);
	    	r.setRestId(rest.getRestId());
	    	r.setRestName(rest.getRestName());
	    	r.setRestCloseTime(rest.getRestCloseTime());
	    	r.setRestOpenTime(rest.getRestOpenTime());
	    	r.setRestDescription(rest.getRestDescription());
	    	r.setIsActive(rest.getIsActive());
	    	r.setCurrentStatus(rest.getCurrentStatus());
	    	r.setUpdateAt(LocalDateTime.now());
	    	String imageName;
	    	try {
	    		System.err.println(Objects.nonNull(rest.getImageName()));
	    		if(Objects.nonNull(rest.getImageName())) 
	    		{
	    		System.err.println("inside if");
				imageName=this.iService.uploadImage(rest.getImageName(), FolderName.RESTAURANT);
				r.setRestImageName(imageName);
	    		}else 
	    		{
	  
	    			//r.setRestImageName(r.getRestImageName());
	    		}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	RestAddress a = new RestAddress();
	    	a.setCity(rest.getCity());
	    	a.setLatitude(rest.getLatitude());
	    	a.setLongitude(rest.getLongitude());
	    	a.setRestAddressId(rest.getRestAddressId());
	    	a.setRestContect(rest.getRestContect());
	    	a.setState(rest.getState());
	    	a.setStreet(rest.getStreet());
	    	a.setZipCode(rest.getZipCode());
	    	r.setRestAddress(a);
	    	
	    	 this.repo.save(r);
	    	 ViewRestaurantResponse res = this.setDataInViewRestaurantResponse(r);
	    	response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.RESTAURANT_UPDATE_SUCCESS);
	    	response.put(AppConstant.DATA, res);
	    	return ResponseEntity.status(HttpStatus.OK).body(response);
	    }    
	    else {
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.REST_ALREADY_EXIST);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	    }
	}

	@Override
	public ResponseEntity<?> viewRestaurantOfOwner(Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		User owner = this.urepo.findByEmail(p.getName());
		List<Restaurant> rest = this.repo.getRestaurantByOwnerId(owner.getUserId());
		List<ViewRestaurantOfOwnerByAdmin> viewList = rest.stream().map(this::restToviewRestResponseofOwner).collect(Collectors.toList());
		response.put(AppConstant.RESPONSE_MESSAGE, viewList);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	private ViewRestaurantOfOwnerByAdmin restToviewRestResponseofOwner(Restaurant r) 
	{
		ViewRestaurantOfOwnerByAdmin v = new ViewRestaurantOfOwnerByAdmin();
		v.setRestId(r.getRestId());
		v.setCity(r.getRestAddress().getCity());
		v.setCreatedat(r.getCreatedAt());
		v.setCurrentStatus(r.getCurrentStatus());
		v.setFssaiLicenceNo(r.getFssaiLicense().getLicenseNumber());
		v.setFssaiLicencePhoto(r.getFssaiLicense().getFssaiLicensePhoto());
		v.setGstLicenceNo(r.getGstRegistration().getLicenseNumber());
		v.setGstLicencePhoto(r.getGstRegistration().getGstlicensePhoto());
		v.setIsActive(r.getIsActive());
		v.setRestCloseTime(r.getRestCloseTime());
		v.setRestContect(r.getRestAddress().getRestContect());
		v.setRestOpenTime(r.getRestOpenTime());
		v.setRestImage(r.getRestImageName());
		v.setRestName(r.getRestName());
		v.setRestDescription(r.getRestDescription());
		v.setState(r.getRestAddress().getState());
		v.setIsApprove(r.getIsApprove());	
		v.setIsBlocked(r.getIsBlocked());
		return v;
	}

	@Override
	public ResponseEntity<?> getRestaurantByRestId(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Restaurant r = this.repo.findByRestId(restId);
		ViewRestaurantResponse v = setDataInViewRestaurantResponse(r);
		response.put(AppConstant.RESPONSE_MESSAGE, v);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private ViewRestaurantResponse setDataInViewRestaurantResponse(Restaurant r) {
		// TODO Auto-generated method stub
		ViewRestaurantResponse v = new ViewRestaurantResponse();
		v.setCity(r.getRestAddress().getCity());
		v.setCreatedAt(r.getCreatedAt().toString());
		v.setCurrentStatus(r.getCurrentStatus());
		v.setIsActive(r.getIsActive());
		v.setIsApprove(r.getIsApprove());
		v.setIsBlocked(r.getIsBlocked());
		v.setLatitude(r.getRestAddress().getLatitude());
		v.setLongitude(r.getRestAddress().getLongitude());
		v.setRestAddressId(r.getRestAddress().getRestAddressId());
		v.setRestCloseTime(r.getRestCloseTime());
		v.setRestOpenTime(r.getRestOpenTime());
		v.setRestContect(r.getRestAddress().getRestContect());
		v.setRestDescription(r.getRestDescription());
		v.setRestId(r.getRestId());
		v.setRestImage(r.getRestImageName());
		v.setRestName(r.getRestName());
		v.setState(r.getRestAddress().getState());
		v.setStreet(r.getRestAddress().getStreet());
		v.setZipCode(r.getRestAddress().getZipCode());
		v.setGstLicenceNo(r.getGstRegistration().getLicenseNumber());
		v.setFssaiLicenceNo(r.getFssaiLicense().getLicenseNumber());
		v.setFssaiLicencePhoto(r.getFssaiLicense().getFssaiLicensePhoto());
		v.setGstLicencePhoto(r.getGstRegistration().getGstlicensePhoto());
		System.err.println(v);
		return v;
	}

	@Override
	public ResponseEntity<?> deleteRestaurant(String restId) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		Restaurant r = this.repo.findByRestId(restId);
		r.setIsActive(false);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.RESTAURANT_DELETE_SUCCESS);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<?> getRestaurauntNameAndCategoryofOwner(Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		User owner = this.urepo.findByEmail(p.getName());
       
		
		return null;
	}

	@Override
	public ResponseEntity<?> getRestaurantNameOfOWner(Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		User owner = this.urepo.findByEmail(p.getName());
		List<Restaurant> rest = this.repo.getApproveRestaurantByOwnerId(owner.getUserId(),AppConstant.VERIFIED);
		List<RestNameResponse> viewList = rest.stream().map(this::restTorestname).collect(Collectors.toList());
		response.put(AppConstant.RESPONSE_MESSAGE, viewList);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private RestNameResponse restTorestname(Restaurant r) 
	{
		RestNameResponse rn = new RestNameResponse();
		rn.setRid(r.getRestId());
		rn.setRName(r.getRestName());
		return rn;
	}

}
