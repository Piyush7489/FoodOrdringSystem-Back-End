package com.dollop.fos.reposatory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dollop.fos.entity.UserRole;

public interface IUserRoleRepo extends JpaRepository<UserRole, String> {

}
