package com.dollop.fos.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.requests.RestSaveRequest;
import com.dollop.fos.service.IRestaurantService;

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
		return this.service.getDataofRestaurant(page, size);
	}                                         
          

      
}




 



