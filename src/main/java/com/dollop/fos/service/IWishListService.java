package com.dollop.fos.service;

import java.security.Principal;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.AddWishListRequest;

public interface IWishListService {

	 public ResponseEntity<?> addToWishList(AddWishListRequest request,Principal p);
	 public ResponseEntity<?> removeToWishList(String foodId,Principal p);
	 public ResponseEntity<?> viewWishList(Principal p);
	
}
