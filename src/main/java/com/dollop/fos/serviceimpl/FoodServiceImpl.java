package com.dollop.fos.serviceimpl;

import org.springframework.stereotype.Service;

import com.dollop.fos.service.IFoodService;
@Service
public class FoodServiceImpl implements IFoodService {
//
//	@Autowired
//	private IFoodRepo frepo;
//	@Autowired
//	private ICategoryRepo crepo;
//	@Autowired
//	private IRestaurantRepo restRepo;
//	@Autowired
//	private ModelMapper modelMapper;
//	@Autowired
//	private IImageService iService;
//	
//	@Override
//	public ResponseEntity<?> addFood(AddFoodRequest request) {
//		 Map<String, Object> response = new HashMap<>();
//	     Food f3 = this.setDatainFood(request);
//		List<Food> listOfFoodByResto = f3.getRestaurant().getListOfFood();
//		for(Food food :listOfFoodByResto) 
//		{
//		  if(f3.getFoodName().equalsIgnoreCase(food.getFoodName()))
//		  {
//			 response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.FOUND_FOUND);
//			 return ResponseEntity.status(HttpStatus.OK).body(response);
//		  }	
//		}
//		 f3.setFoodCreatedAt(new Date().toString());
//	     Category c = this.crepo.findCatnameByCatId(f3.getCategory().getCatId());
//	     f3.setFoodCategoryName(c.getCatName());
//	     
//	     String imagename;
//		try {
//			imagename = iService.uploadImage(request.getImageName(),FolderName.FOOD);
//			f3.setImageName(imagename);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		  frepo.save(f3);
//		 response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.FOOD_ADD_SUCCESS);
//		 return ResponseEntity.status(HttpStatus.OK).body(response);
//	}
//	public Food setDatainFood(AddFoodRequest request)
//	{
//		Food food = new Food();
//		
//		Restaurant restaurant = this.restRepo.findByRestId(request.getRestaurantId());
//		Category category = this.crepo.findByCatId(request.getCategoryId());
//		food.setFoodDescription(request.getFoodDescription());
//		food.setRestaurant(restaurant);
//		food.setCategory(category);
//		food.setIsAvailable(true);
//		food.setFoodName(request.getFoodName());
//		food.setFoodPrice(request.getFoodPrice());
//		food.setFoodId(UUID.randomUUID().toString());
//		return food;
//	}
	

}
