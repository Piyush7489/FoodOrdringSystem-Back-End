package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.CategorySaveRequest;
import com.dollop.fos.requests.CategoryUpdateRequest;
import com.dollop.fos.requests.GlobalCategoryRequest;
import com.dollop.fos.service.ICategoryService;
import com.dollop.fos.service.IGlobalCategoryService;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

//	@Autowired
//	private ICategoryService catService;
//
//	@PostMapping("/save")
//	public ResponseEntity<?> addCategory(@RequestBody CategorySaveRequest category)
//	{
//		return  this.catService.createCategory(category);
//	}
//	
//	@PutMapping("/update")
//	public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdateRequest cur)
//	{
//		return this.catService.updateCategory(cur);
//	}
	
}
