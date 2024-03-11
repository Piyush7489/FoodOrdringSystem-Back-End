package com.dollop.fos.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.service.IFoodService;



@RestController
@RequestMapping("/api/v1/food")
@CrossOrigin
public class FoodController {

	@Autowired
	private IFoodService service;
	
    @PostMapping("/save")
	public ResponseEntity<?> addFood(@RequestParam ("food")String food,@RequestParam(value = "foodImage",required = false) MultipartFile foodImage)
	{
		return this.service.addFood(food,foodImage);
	}
    @GetMapping("/view-food")
    public ResponseEntity<?> viewAllFood(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size,Principal p)
    {
    	return this.service.viewAllFood(page, size,p);
    }
    @PutMapping("/update-food")
    public ResponseEntity<?> updateFood(@RequestParam("food") String food,@RequestParam(value = "foodImage",required = false)  MultipartFile foodImage)
    {
    	return this.service.updateFood(food, foodImage);
    }
}
