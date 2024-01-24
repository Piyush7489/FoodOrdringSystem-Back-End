package com.dollop.fos.paginationdto;

import java.util.List;

import com.dollop.fos.response.AllCategoryResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PageAllCategoryResponse {
	private List<AllCategoryResponse> contents;
	private Integer pageNo;
	private Integer pageSize;
	private Long totalElements;
	private Integer totalPages;
	private Boolean lastPage; 
	
	
}
