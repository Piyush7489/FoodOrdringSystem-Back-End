package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.CategorySaveRequest;

public interface ICategoryService {

	
	public ResponseEntity<?> createCategory(CategorySaveRequest categorySaveRequest);
}
