package com.dollop.fos.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.Category;
import com.dollop.fos.entity.Restaurant;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.ICategoryRepo;
import com.dollop.fos.reposatory.IRestaurantRepo;
import com.dollop.fos.requests.CategorySaveRequest;
import com.dollop.fos.service.ICategoryService;
@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private IRestaurantRepo restRepo;
	@Autowired
	private ICategoryRepo crepo;
	@Override
	public ResponseEntity<?> createCategory(CategorySaveRequest category) {
		Map<String,Object > response = new HashMap<>();
		Category c = this.setCatData(category);
		Category c2 = this.crepo.findBycatNameAndRestId(c.getCatName(), category.getRestId());
		if(Objects.nonNull(c2)) 
		{
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY__FOUND);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		this.crepo.save(c);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.CATEGORY_ADD_SUCCESS);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	public Category setCatData(CategorySaveRequest cat) 
	{
		Category c = new Category();
		Restaurant r = this.restRepo.findById(cat.getRestId()).get();
		c.setCatId(UUID.randomUUID().toString());
		c.setCatName(cat.getCatName());
		c.setCatDescription(cat.getCatDescription());
		c.setIsActive(true);
		c.setRestaurant(r);
		return c;
	}

}
