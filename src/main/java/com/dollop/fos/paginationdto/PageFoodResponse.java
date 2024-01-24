package com.dollop.fos.paginationdto;

import java.util.List;

import com.dollop.fos.response.FoodResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageFoodResponse {
	
	private List<FoodResponse> contents;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private Boolean lastPage; 
}
