package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.GlobalCategoryRequest;
import com.dollop.fos.service.IGlobalCategoryService;
@RestController
@RequestMapping("/api/v1/globalCategory")
public class GlobalCategoryController {

	@Autowired
	private IGlobalCategoryService service;
	@PostMapping("/save")
	public ResponseEntity<?> createGlobalCategory(@RequestBody GlobalCategoryRequest request)
	{
		return this.service.createGlobalCategory(request);
	}
}
