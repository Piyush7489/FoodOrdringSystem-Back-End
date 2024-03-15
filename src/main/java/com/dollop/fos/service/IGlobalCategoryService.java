package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.requests.GlobalCategoryRequest;
import com.dollop.fos.requests.RestCategoryRequest;

public interface IGlobalCategoryService {

	public ResponseEntity<?> createGlobalCategory(String request,MultipartFile catImage);
	public ResponseEntity<?> updateGlobalCategory(GlobalCategoryRequest request);
	public ResponseEntity<?> getGlobalCategoryById(String id);
	public ResponseEntity<?> deleteGlobalCategory(String id);
	public ResponseEntity<?> updateActiveStatus(String id,boolean active);
	public ResponseEntity<?> getData(int page,int size);
	public ResponseEntity<?> getAllActiveCategoryName();
	public ResponseEntity<?> getCategoryByRestId(String restId);
	public ResponseEntity<?> getAllCategoryWhoNotHaveThisRestaurant(String restId);
	public ResponseEntity<?> addCategoryInRestaurant(RestCategoryRequest request);
}
