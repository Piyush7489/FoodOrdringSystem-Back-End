package com.dollop.fos.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.requests.RestSaveRequest;
import com.dollop.fos.response.ViewRestaurantResponse;
import com.dollop.fos.service.IRestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/v1/rest")
@CrossOrigin("*")
public class RestaurantController {

	@Autowired
	private IRestaurantService service;

	@PostMapping("/save")
	public ResponseEntity<?> createRestaurant(RestSaveRequest rs,Principal p)
	{

        return this.service.addRestaurant(rs,p);
	}
	@GetMapping("/{id}/{status}")
	public ResponseEntity<?> changeStatus(@PathVariable String id,@PathVariable boolean status)
	{
		return this.service.changeStatus(id, status);
	}
	
	@GetMapping("/dataOfRest")
	public ResponseEntity<?> getdataofRestaurant(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size)
	{
		return this.service.getDataofRestaurant(page,size);
	}                                         
   @PutMapping("/edit-rest")
   public ResponseEntity<?> editRestaurant( @RequestParam("rest") String rest,Principal p,@RequestParam(value = "restimage",required = false) MultipartFile restimage)
   {
	  
	    ObjectMapper o = new ObjectMapper();
		ViewRestaurantResponse updateRequest=null;;
	    try {
			updateRequest = o.readValue(rest,ViewRestaurantResponse.class);
			updateRequest.setImageName(restimage);
			System.err.println(updateRequest);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	   
	   return this.service.editRestaurant(updateRequest, p);
   }
   @GetMapping("/view-owner-rest")
   public ResponseEntity<?> viewOwnerRestaurant(Principal p)
   {
	  
	   return this.service.viewRestaurantOfOwner(p);
   } 
   @GetMapping("/get/{restId}")
   public ResponseEntity<?> viewRestByRestId(@PathVariable String restId)
   {
	   return this.service.getRestaurantByRestId(restId);
   }
   @DeleteMapping("/{restId}")
   public ResponseEntity<?> deleteRestaurant(@PathVariable("restId") String restId)
   {
	   return this.service.deleteRestaurant(restId);
   }
   @GetMapping("/rest-name-of-owner")
   public ResponseEntity<?> getRestnameOfOwners(Principal p)
   {
	return this.service.getRestaurantNameOfOWner(p);   
   }
   @GetMapping("/cat-of-rest/{restId}")
   public ResponseEntity<?> getCategoryOfRestaurant(@PathVariable String restId)
   {
	   return this.service.getCategoryOfRestaurant(restId);
   }
}




 



