package com.dollop.fos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.reposatory.IRestaurantCategoryRepo;
import com.dollop.fos.requests.GlobalCategoryRequest;
import com.dollop.fos.requests.RestCategoryRequest;
import com.dollop.fos.service.IGlobalCategoryService;
@RestController
@RequestMapping("/api/v1/globalCategory")
@CrossOrigin
public class GlobalCategoryController {

	@Autowired
	private IGlobalCategoryService service;
	
	
	@PostMapping("/save")
	public ResponseEntity<?> createGlobalCategory(@RequestParam("category")String categoryRequest,@RequestParam(value = "catImage" ,required = false) MultipartFile catImage)
	{
		System.err.println(catImage);
		return this.service.createGlobalCategory(categoryRequest,catImage);
	}
	@PutMapping("/update")
	public ResponseEntity<?> updateGlobalCategory(@RequestBody GlobalCategoryRequest request)
	{
		return this.service.updateGlobalCategory(request);
	}
	@GetMapping("/{id}")
	public ResponseEntity<?> getGlobalCategoryById(@PathVariable String id)
	{
		System.err.println("...");
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
    @GetMapping("/extra-cat-add/{restId}")
	public ResponseEntity<?>getAllCategoryWhoNotHaveThisRestaurant(@PathVariable String restId)
	{
		return this.service.getAllCategoryWhoNotHaveThisRestaurant(restId);
	}
    @PostMapping("/")
    public ResponseEntity<?> addCategoryInRestaurant(@RequestBody RestCategoryRequest request)
    {
    	return this.service.addCategoryInRestaurant(request);
    }
	@DeleteMapping("/removeCat/{restId}/{categoryId}")
    public ResponseEntity<?> removeCategoryFromRestaurant(@PathVariable String restId,@PathVariable String categoryId)
    {
    	return this.service.removeCategoryFromRestaurant(restId, categoryId);
    }
}

