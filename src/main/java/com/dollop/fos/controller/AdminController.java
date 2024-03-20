package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.DeliveryBoyVerificationRequest;
import com.dollop.fos.service.IAdminService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

	@Autowired
	private IAdminService service;

	@GetMapping("/RestaurantApprove/{restId}")
	public ResponseEntity<?> approveRestaurant(@PathVariable String restId) {
		return this.service.verifyRestaurant(restId);
	}
	@GetMapping("/RestaurantReject/{restId}")
	public ResponseEntity<?> rejectrestaurant(@PathVariable String restId)
	{
		return this.service.unVerifyRestaurant(restId);
	}

	@GetMapping("/RestaurantBlock/{restId}")
	public ResponseEntity<?> blockRestaurant(@PathVariable String restId) {
		System.err.println("block");
		return this.service.blockedRestaurant(restId);
	}

	@GetMapping("/RestaurantUnBlock/{restId}")
	public ResponseEntity<?> UnBlockRestaurant(@PathVariable String restId) {
		return this.service.unblockedRestaurant(restId);
	}

	@PostMapping("/verify")	
	public ResponseEntity<?> verificationOfDeliveryBoy(@RequestBody DeliveryBoyVerificationRequest d)
	{
	   System.out.println("verification controller....");
	   return null;
	}
	@GetMapping("/all")
	public ResponseEntity<?> viewAllCategory()
	{
		return this.service.viewAllCategory();
	}
    @GetMapping("/allRestaurant")
	public ResponseEntity<?> viewAllRestaurant()
	{
		return this.service.viewAllRestaurant();
	}
    @GetMapping("/allverified")
    public ResponseEntity<?> viewAllVerifiedRestaurant()
    {
    	return this.service.viewVerifiedRestaurant();
    }
    @GetMapping("/verificationData/{id}")
    public ResponseEntity<?> verificationOfRestaurantData(@PathVariable String id)
    {
    	return this.service.verificationOfRestaurant(id);
    }
    @GetMapping("/customer-list")
	public ResponseEntity<?> listOfCustomer(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size)
	{
		return this.service.getCustomerList(page, size);
	}    
    @GetMapping("/owner-list")
   	public ResponseEntity<?> getOwnerList(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size)
   	{
   		return this.service.getOwnerList(page, size);
   	}  
    @GetMapping("/owner-rest/{ownerId}")
   	public ResponseEntity<?> getRestOfOwnerByOwnerId(@PathVariable String ownerId)
   	{
    	System.err.println(".....");
   		return this.service.getAllRestaurantofOwnerId(ownerId);
   	}   
    
    @GetMapping("/customer-boy-count")
    public ResponseEntity<?> getCountOfCustomersAndBoy()
    {
    	return this.service.getCountOfCustomersAndBoy();
    }
    
    @GetMapping("/all-boy-list")
    public ResponseEntity<?> getAllDeliveryBoy(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size)
    {
    	return this.service.getAllDeliveryBoy(page, size);
    }
}
