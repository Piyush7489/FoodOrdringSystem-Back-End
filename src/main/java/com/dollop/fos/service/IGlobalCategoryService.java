package com.dollop.fos.service;

import org.springframework.http.ResponseEntity;

import com.dollop.fos.requests.GlobalCategoryRequest;

public interface IGlobalCategoryService {

	public ResponseEntity<?> createGlobalCategory(GlobalCategoryRequest request);
}
