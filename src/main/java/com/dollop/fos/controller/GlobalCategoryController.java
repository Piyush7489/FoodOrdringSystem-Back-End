package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dollop.fos.requests.GlobalCategoryRequest;
import com.dollop.fos.service.IGlobalCategoryService;

import jakarta.persistence.criteria.Path;
@RestController
@RequestMapping("/api/v1/globalCategory")
@CrossOrigin
public class GlobalCategoryController {

	@Autowired
	private IGlobalCategoryService service;
	@PostMapping("/save")
	public ResponseEntity<?> createGlobalCategory(@RequestBody GlobalCategoryRequest request)
	{
		return this.service.createGlobalCategory(request);
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateGlobalCategory(@RequestBody GlobalCategoryRequest request)
	{
		return this.service.updateGlobalCategory(request);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getGlobalCategoryById(@PathVariable String id)
	{
		return this.service.getGlobalCategoryById(id);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGlobalCategoryById(@PathVariable String id)
	{
		return this.service.deleteGlobalCategory(id);
	}
	@GetMapping("/{id}/{status}")
	public ResponseEntity<?> updateActiveStatus(@PathVariable("id") String id,@PathVariable boolean status)
	{
		System.err.println(id);
		return this.service.updateActiveStatus(id, status);
	} 
	@GetMapping("/data")
	public ResponseEntity<?> getdata(@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "10")int size)
	{
		return this.service.getData(page, size);
	}
	@GetMapping("/AllCatName")
	public ResponseEntity<?> getAllActiveCatName()
	{
		return this.service.getAllActiveCategoryName();
	}
	@GetMapping("/cat-name/{restId}")
	public ResponseEntity<?> findCatnameByRestId(@PathVariable String restId)
	{
		return this.service.getCategoryByRestId(restId);
	}
}
