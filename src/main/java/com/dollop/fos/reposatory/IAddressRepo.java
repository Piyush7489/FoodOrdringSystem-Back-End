package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.Address;

public interface IAddressRepo extends JpaRepository<Address, String> {

}
