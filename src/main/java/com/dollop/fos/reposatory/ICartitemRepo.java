package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.CartItem;

public interface ICartitemRepo extends JpaRepository<CartItem, String> {

}
