package com.dollop.fos.paginationdto;



import java.util.List;

import com.dollop.fos.response.ViewRestaurantResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PageRestaurantResponsee {

    
	private List<ViewRestaurantResponse> contents;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private Boolean lastPage; 
}

