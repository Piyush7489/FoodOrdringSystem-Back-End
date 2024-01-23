package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.Role;

public interface IRoleRepo extends JpaRepository<Role, String> {

}
