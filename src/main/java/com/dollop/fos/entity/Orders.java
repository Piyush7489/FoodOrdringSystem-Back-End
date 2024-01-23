package com.dollop.fos.entity;

import java.time.LocalDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderId;
    @ManyToOne
    @JoinColumn(name = "userIdfk")
    @JsonIgnoreProperties(value = "listOfOrders")
	private User user;
	@OneToMany(mappedBy = "order")
	private List<OrderItem> orderItems;
	@OneToOne(mappedBy = "order")
	private Bill bill;
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	private double totalAmount;
	private LocalDateTime orderDate;
	
}
