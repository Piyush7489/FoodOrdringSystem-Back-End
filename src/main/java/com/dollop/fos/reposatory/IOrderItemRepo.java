package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.OrderItem;

public interface IOrderItemRepo extends JpaRepository<OrderItem, String> {

}
