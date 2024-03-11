package com.dollop.fos.service;



import java.security.Principal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.requests.UpdateFoodRequest;

public interface IFoodService {

	public ResponseEntity<?> addFood(String foodRequest,MultipartFile foodImage);
	public ResponseEntity<?> updateFood(String request,MultipartFile foodImage);
//	public ResponseEntity<?> viewFoodByRestId(int page,int size,String restId);
	public ResponseEntity<?> viewAllFood(int page,int size,Principal p);
}
