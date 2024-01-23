package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.Cart;

public interface ICartRepo extends JpaRepository<Cart, String	> {

}
