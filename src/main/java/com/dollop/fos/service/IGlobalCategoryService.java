package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.GlobalCategoryRequest;

public interface IGlobalCategoryService {

	public ResponseEntity<?> createGlobalCategory(GlobalCategoryRequest request);
	public ResponseEntity<?> updateGlobalCategory(GlobalCategoryRequest request);
	public ResponseEntity<?> getGlobalCategoryById(String id);
	public ResponseEntity<?> deleteGlobalCategory(String id);
	public ResponseEntity<?> updateActiveStatus(String id,boolean active);
	public ResponseEntity<?> getData(int page,int size);
	public ResponseEntity<?> getAllActiveCategoryName();
	public ResponseEntity<?> getCategoryByRestId(String restId);
}
