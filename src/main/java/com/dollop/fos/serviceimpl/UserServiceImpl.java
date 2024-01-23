package com.dollop.fos.serviceimpl;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.dollop.fos.customexceptions.ResourceFoundException;
import com.dollop.fos.entity.Role;
import com.dollop.fos.entity.User;
import com.dollop.fos.entity.UserRole;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.helper.FolderName;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.requests.SignupRequest;
import com.dollop.fos.service.IUserService;
@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder pass;
	
	@Autowired
	private ModelMapper modelmapper;
	
	
	@Override
	public ResponseEntity<?> createUser(SignupRequest user) {
		// TODO Auto-generated method stub
		Map<Object,Object> response = new HashMap<>();
		User local = this.userRepo.findByEmail(user.getEmail().trim());
		if (local != null) {
			throw new ResourceFoundException(AppConstant.EMAIL_IN_USE);
		}
		local = signupToUser(user);
		
		Set<UserRole> roles = new HashSet<>();
		Role role = new Role();
		if (user.getTempRole().equalsIgnoreCase("boy")) {
			role.setRoleId(10L);
			role.setRoleName("BOY");
			UserRole userRole = new UserRole();
			userRole.setUser(local);
			userRole.setRole(role);
			roles.add(userRole);
			local.setIsActive(false);
			
		}
     else if (user.getTempRole().equalsIgnoreCase("owner")) {
			role.setRoleId(20L);
			role.setRoleName("OWNER");
			UserRole userRole = new UserRole();
			userRole.setUser(local);
			userRole.setRole(role);
			roles.add(userRole);
			local.setIsActive(true);
		}
     else {	
			role.setRoleId(30L);
			role.setRoleName("CUSTOMER");
			UserRole userRole = new UserRole();
			userRole.setUser(local);
			userRole.setRole(role);
			roles.add(userRole);
			local.setIsActive(true);
		}
		local.setUserId(UUID.randomUUID().toString());
		local.setProfilePhoto(FolderName.PROFILE_PHOTO+"/user.png");
		local.setCreateAt(LocalDate.now());
		local.setUserRole(roles);
		local.setPassword(pass.encode(local.getPassword()));
		local = this.userRepo.save(local);
		if(Objects.nonNull(local))
		{
			response.put(AppConstant.DATA, user.getTempRole()+AppConstant.USER_CREATED);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		else
		{
			response.put(AppConstant.ERROR, AppConstant.USER_NOT_CREATED);
			return ResponseEntity.status(HttpStatus.CREATED).body(AppConstant.USER_NOT_CREATED);
		}
	}

	public User signupToUser(SignupRequest request) {
		User user = this.modelmapper.map(request, User.class);
		return user;
	}
	@Override
	public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user = this.userRepo.getUserByName(useremail);
		if(user.isEmpty()) 
		{
			throw new ResourceFoundException(AppConstant.USER_NOT_FOUND);
		}	List<GrantedAuthority> authorities = user.get().getUserRole().stream().map(role->new SimpleGrantedAuthority(role.getRole().getRoleName())).collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(useremail, user.get().getPassword(), authorities);
     }

	@Override
	public String getUserRole(String email) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> findUserRolesByEmail = this.userRepo.findUserEmailAndRoleNameByEmail(email);
		Map<String, Object> map = findUserRolesByEmail.get(0);
		Object object = map.get("roleName");
		return object.toString();
	}
	
	
	
}


