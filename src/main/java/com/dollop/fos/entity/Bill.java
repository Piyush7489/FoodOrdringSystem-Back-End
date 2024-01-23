package com.dollop.fos.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Bill {
    @Id

	private String billId;
	private String billNo;
	private LocalDateTime createAt = LocalDateTime.now();
	@OneToOne
	@JoinColumn(name="orderIdfk")
	private Orders order;
	@OneToOne
	@JoinColumn(name="userIdfk")
	private User customer;
	
}
