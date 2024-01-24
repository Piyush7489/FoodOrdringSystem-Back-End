package com.dollop.fos.serviceimpl;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dollop.fos.entity.Food;
import com.dollop.fos.entity.User;
import com.dollop.fos.entity.WishList;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IFoodRepo;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.reposatory.IWishListRepo;
import com.dollop.fos.requests.AddWishListRequest;
import com.dollop.fos.response.WishListRespose;
import com.dollop.fos.service.IWishListService;
@Service
public class WishListServiceImpl implements IWishListService {

	@Autowired
	private IWishListRepo wRepo;
	@Autowired
	private IFoodRepo fRepo;
	@Autowired
	private IUserRepo uRepo;
	
	@Override
	public ResponseEntity<?> addToWishList(AddWishListRequest request, Principal p) {
		// TODO Auto-generated method stub
		Map<String, Object>response = new HashMap<>();
		WishList wishList  = this.setDataInWishList(request,p);
		List<WishList> existingItems = wRepo.findByUserIdAndFoodId(wishList.getCostomer().getUserId(),wishList.getFood().getFoodId());
		if(existingItems.isEmpty()) 
		{
			 wRepo.save(wishList);
			 response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.ADD_WISHLIST);
			 return ResponseEntity.status(HttpStatus.OK).body(response);
		}else 
		{
			return this.removeToWishList(request.getFoodId(), p);
		}
		
	}

	private WishList setDataInWishList(AddWishListRequest request,Principal p) {
		// TODO Auto-generated method stub
		User customer = this.uRepo.findByEmail(p.getName());
		Food food = this.fRepo.findFoodByFoodId(request.getFoodId());
		WishList w = new WishList();
		w.setCostomer(customer);
		w.setFood(food);
		return w;
	
	}

	@Override
	public ResponseEntity<?> viewWishList(Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		User u = this.uRepo.findByEmail(p.getName());
		List<WishList> list = wRepo.findWishListByUserId(u.getUserId());
		List<WishListRespose> viewWishList = this.setDataInWishListResponse(list);
		response.put(AppConstant.DATA, viewWishList);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private List<WishListRespose> setDataInWishListResponse(List<WishList> list) {
		// TODO Auto-generated method stub
		List<WishListRespose> response =new ArrayList<>();
		for(int i=0;i<list.size();i++) 
		{
		    WishList w = list.get(i);
		    Food f = w.getFood();
		    WishListRespose r = new WishListRespose();
		    r.setFoodCategoryName(f.getFoodName());
		    r.setFoodCategoryName(f.getFoodCategoryName());
		    r.setFoodDescription(f.getFoodDescription());
		    r.setFoodImage(f.getImageName());
		    r.setFoodName(f.getFoodName());
		    r.setFoodPrice(f.getFoodPrice());
		    r.setFoodisAvailable(f.getIsAvailable());
		    r.setId(f.getFoodId());
		    response.add(r);
		}
		return response;
	}

	@Override
	public ResponseEntity<?> removeToWishList(String foodId, Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object>response = new HashMap<>();
		User u = this.uRepo.findByEmail(p.getName());
		List<WishList> existingItems = this.wRepo.findByUserIdAndFoodId(u.getUserId(), foodId);
		WishList existItem = existingItems.get(0);
		wRepo.delete(existItem);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.REMOVE_ITEM_FROM_WISHLIST);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
