package com.dollop.fos.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@ToString
@Setter
@Getter
public class RestaurantStatusCountResponse {

	private Long activeCount;
	private Long inactiveCount;
	private Long blockedCount;
	private Long unblockedCount;
	private Long verifiedCount;
	private Long unverifiedCount;
	private Long totalCount;
	
	
	public RestaurantStatusCountResponse(Long activeCount, Long inactiveCount, Long blockedCount, Long unblockedCount, Long verifiedCount, Long unverifiedCount, Long totalCount) {
        this.activeCount = activeCount;
        this.inactiveCount = inactiveCount;
        this.blockedCount = blockedCount;
        this.unblockedCount = unblockedCount;
        this.verifiedCount = verifiedCount;
        this.unverifiedCount = unverifiedCount;
        this.totalCount = totalCount;
    }

}
