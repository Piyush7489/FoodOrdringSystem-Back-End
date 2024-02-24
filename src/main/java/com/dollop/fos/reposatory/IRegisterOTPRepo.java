package com.dollop.fos.reposatory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.RegisterOtp;

public interface IRegisterOTPRepo extends JpaRepository<RegisterOtp,String> {

	Optional<RegisterOtp> findByEmail(String email);
    
}
