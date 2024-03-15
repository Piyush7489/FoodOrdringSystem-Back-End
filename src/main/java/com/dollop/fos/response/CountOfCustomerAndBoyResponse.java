package com.dollop.fos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CountOfCustomerAndBoyResponse {
	
	private Long customerCount;
	private Long boyCount;
	private Long ownerCount;
	private Long totalCountOfUser;

}
