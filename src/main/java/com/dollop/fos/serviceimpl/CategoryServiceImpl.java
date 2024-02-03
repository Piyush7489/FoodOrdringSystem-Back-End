package com.dollop.fos.serviceimpl;

import org.springframework.stereotype.Service;

import com.dollop.fos.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {

//	@Autowired
//	private IRestaurantRepo restRepo;
//	@Autowired
//	private ICategoryRepo crepo;
//
//	@Override
//	public ResponseEntity<?> createCategory(CategorySaveRequest category) {
//		Map<String, Object> response = new HashMap<>();
//		Category c = this.setCatData(category);
//		Optional<Restaurant> rest = this.restRepo.findById(category.getRestId());
//		Category c2 = this.crepo.findBycatNameAndRestId(c.getCatName(), category.getRestId());
//		if (Objects.nonNull(c2)) {
//			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY__FOUND);
//			return ResponseEntity.status(HttpStatus.OK).body(response);
//
//		} else {
//			if (Objects.nonNull(rest)) {
//				Restaurant restaurant = rest.get();
//				if (restaurant.getIsApprove().equalsIgnoreCase(AppConstant.VERIFIED)) {
//					this.crepo.save(c);
//					response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_ADD_SUCCESS);
//					return ResponseEntity.status(HttpStatus.CREATED).body(response);
//				} else {
//					response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.RESTAURANT_UNVERIFIED);
//					return ResponseEntity.status(HttpStatus.OK).body(response);
//				}
//			} else {
//				response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.RESTAURANT_NOT_FOUND);
//				return ResponseEntity.status(HttpStatus.OK).body(response);
//			}
//		}
//	}
//
//	public Category setCatData(CategorySaveRequest cat) {
//		Category c = new Category();
//		Restaurant r = this.restRepo.findById(cat.getRestId()).get();
//		c.setCatId(UUID.randomUUID().toString());
//		c.setCatName(cat.getCatName().trim());
//		c.setCatDescription(cat.getCatDescription().trim());
//		c.setIsActive(true);
////    	c.setRestaurant(r);
//		return c;
//	}
//
//	@Override
//	public ResponseEntity<?> updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
//		// TODO Auto-generated method stub
//		Map<String, Object> response = new HashMap<>();
//		Category category = this.setCatData(categoryUpdateRequest);
//		Category category2 = this.crepo.findById(category.getCatId()).get();
//		 Category findBycatNameAndRestId = this.crepo.findBycatNameAndRestId(category.getCatName(),
//		            categoryUpdateRequest.getRestId());
////		List<Category> findCategoriesByParameters = this.crepo.findCategoriesByParameters(categoryUpdateRequest.getCatId(), categoryUpdateRequest.getRestId(), categoryUpdateRequest.getCatName());
//		if(category2.getCatName().equals(categoryUpdateRequest.getCatName()))
//		{
//			System.err.println("SAME NAME");
//			if(categoryUpdateRequest.getCatId().equals(category2.getCatId()))
//			{
//				category.setCatId(categoryUpdateRequest.getCatId());
//		        this.crepo.save(category);
//		        response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_UPDATE_SUCCESS);
//		        return ResponseEntity.status(HttpStatus.OK).body(response);
//			}else {
//				response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_NOT_FOUND);
//		        return ResponseEntity.status(HttpStatus.OK).body(response);
//			}
//		}else {
//			System.err.println("NAME NOT BE SAME");
//			if(Objects.nonNull(findBycatNameAndRestId))
//			{ 
//				System.err.println(findBycatNameAndRestId);
//				response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY__FOUND);
//		        return ResponseEntity.status(HttpStatus.OK).body(response);
//			}else {
//				category.setCatId(categoryUpdateRequest.getCatId());
//		        this.crepo.save(category);
//		        response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_UPDATE_SUCCESS);
//		        return ResponseEntity.status(HttpStatus.OK).body(response);
//			}
//		}
//		
//	}
//
//	public Category setCatData(CategoryUpdateRequest cat) {
//		Category c = new Category();
//		Restaurant r = this.restRepo.findById(cat.getRestId()).get();
//		c.setCatId(cat.getCatId().trim());
//		c.setCatName(cat.getCatName().trim());
//		c.setCatDescription(cat.getCatDescription().trim());
//		c.setIsActive(cat.getIsActive());
////		c.setRestaurant(r);
//		return c;
//	}

}
