package com.dollop.fos.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class WishList {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishListid;
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food  food;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User costomer;
   
}
