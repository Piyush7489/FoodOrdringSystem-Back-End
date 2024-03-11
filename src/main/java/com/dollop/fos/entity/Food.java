package com.dollop.fos.entity;





import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
public class Food {
	@Id
	private String foodId;
	private String foodName;
	private Long foodPrice;
	private LocalDateTime foodCreatedAt;
	private String imageName;
	private Boolean isAvailable;
	private String foodDescription;
	@ManyToOne
	private RestaurantCategory restCategory;

}
