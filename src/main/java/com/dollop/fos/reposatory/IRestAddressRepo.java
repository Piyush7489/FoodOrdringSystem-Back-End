package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.RestAddress;

public interface IRestAddressRepo extends JpaRepository<RestAddress, String> {

}
