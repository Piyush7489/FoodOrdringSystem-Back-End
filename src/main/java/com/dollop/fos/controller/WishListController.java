package com.dollop.fos.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.AddWishListRequest;
import com.dollop.fos.service.IWishListService;

@RestController
@RequestMapping("/api/v1/wish")
public class WishListController {

	@Autowired
	private IWishListService wService;
	
	@PostMapping("/add")
	public ResponseEntity<?> addToWishList(@RequestBody AddWishListRequest request,Principal p)
	{
		return this.wService.addToWishList(request,p);
	}
	
	@GetMapping("/view")
	public ResponseEntity<?> viewWisList(Principal p)
	{
		return this.wService.viewWishList(p);
	}
	
	@DeleteMapping("/remove/{foodId}")
	public ResponseEntity<?> removeToWishList(@PathVariable("foodId") String foodId,Principal p)
	{
		return this.wService.removeToWishList(foodId, p);
	}
	
}
