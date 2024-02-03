package com.dollop.fos.reposatory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dollop.fos.entity.User;

public interface IUserRepo extends JpaRepository<User, String> {

	User findByEmail(String email);

	@Query("SELECT CASE WHEN u.isActive = true THEN true ELSE false END FROM User u WHERE u.email = :email")
	boolean findIsActiveByEmail(@Param("email") String email);

	@Query("SELECT u FROM User u WHERE u.email=:email")
	public Optional<User> getUserByName(String email);

	@Query("SELECT u FROM User u JOIN UserRole ur ON u.userId = ur.user.userId "
			+ "JOIN Role r ON ur.role.roleId = r.roleId " + "WHERE ur.role.roleId = :roleId OR r.roleName = :roleName")
	Page<User> findAllByRoleIdAndRoleName(String roleName, Long roleId, Pageable p);

	@Query("SELECT NEW map(u.email as email, r.roleName as roleName) FROM User u LEFT JOIN u.userRole ur LEFT JOIN ur.role r WHERE u.email = :email")
	List<Map<String, Object>> findUserEmailAndRoleNameByEmail(@Param("email") String email);
	
	@Query("SELECT r.roleName " +
	           "FROM User u " +
	           "JOIN u.userRole ur " +
	           "JOIN Role r ON ur.role.roleId = r.roleId " +
	           "WHERE u.email = :email")
	    String findRoleNameByEmail(@Param("email") String email);

}
