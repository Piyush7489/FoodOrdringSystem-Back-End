package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.Bill;

public interface IBillRepo extends JpaRepository<Bill, String> {

}
