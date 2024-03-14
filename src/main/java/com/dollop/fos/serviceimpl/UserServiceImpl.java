package com.dollop.fos.serviceimpl;

import java.io.IOException;
import java.security.Principal;
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
import org.springframework.web.multipart.MultipartFile;

import com.dollop.fos.customexceptions.ResourceFoundException;
import com.dollop.fos.entity.Role;
import com.dollop.fos.entity.User;
import com.dollop.fos.entity.UserRole;
import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.helper.FolderName;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.requests.ChangePasswordRequest;
import com.dollop.fos.requests.SignupRequest;
import com.dollop.fos.requests.UserUpdateRequest;
import com.dollop.fos.response.UserResponse;
import com.dollop.fos.service.IUserService;
import com.dollop.fos.utility.IImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class UserServiceImpl implements IUserService,UserDetailsService {

	@Autowired
	private IUserRepo userRepo;
	
	@Autowired
	private BCryptPasswordEncoder pass;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private IImageService imageService;
	@Autowired
   private ObjectMapper mapper;
	
	
	@Override
	public ResponseEntity<?> createUser(SignupRequest user) {
		// TODO Auto-generated method stub
		Map<Object,Object> response = new HashMap<>();
		User local = this.userRepo.findByEmail(user.getEmail().trim());
		if (local != null) {
			response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.EMAIL_IN_USE);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		UserRole userRole = new UserRole();
		local = trimObj(user);
		Set<UserRole> roles = new HashSet<>();
		Role role = new Role();
		if (user.getTempRole().equalsIgnoreCase(AppConstant.USER_ROLE_BOY)) {
			role.setRoleId(AppConstant.ROLE_ID_BOY);
			role.setRoleName(AppConstant.USER_ROLE_BOY);
			local.setIsActive(false);
			
		}
     else if (user.getTempRole().equalsIgnoreCase(AppConstant.USER_ROLE_OWNER)) {
			role.setRoleId(AppConstant.ROLE_ID_OWNER);
			role.setRoleName(AppConstant.USER_ROLE_OWNER);
			local.setIsActive(true);
		}
     else {	
			role.setRoleId(AppConstant.ROLE_ID_CUSTOMER);
			role.setRoleName(AppConstant.USER_ROLE_CUSTOMER);
			local.setIsActive(true);
		}
		userRole.setUser(local);
		userRole.setRole(role);
		roles.add(userRole);
		local.setUserId(UUID.randomUUID().toString());
		local.setProfilePhoto(FolderName.PROFILE_PHOTO+AppConstant.DEFAULT_USER_IMAGE);
		local.setCreateAt(LocalDate.now());
		local.setUserRole(roles);
		local.setPassword(pass.encode(local.getPassword()));
		
		local = this.userRepo.save(local);
		if(Objects.nonNull(local))
		{
			response.put(AppConstant.RESPONSE_MESSAGE, user.getTempRole()+AppConstant.USER_CREATED);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		}
		else
		{
			response.put(AppConstant.ERROR, AppConstant.USER_NOT_CREATED);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	}
	
	public User trimObj(SignupRequest user)
	{
		User u = new User();
		u.setFirstName(user.getFirstName().trim());
		u.setLastName(user.getLastName().trim());
		u.setEmail(user.getEmail().trim());
		u.setMob(user.getMob().trim());
		u.setPassword(user.getPassword().trim());
		u.setTempAddress(user.getTempAddress());
		return u;
	}

	public User signupToUser(SignupRequest request) {
		User user = this.modelmapper.map(request, User.class);
		return user;
	}
	@Override
	public UserDetails loadUserByUsername(String useremail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		System.err.println(useremail);
		Optional<User> user = this.userRepo.getUserByName(useremail);
		System.err.println(user.isEmpty());
		if(user.isEmpty()) 
		{
			throw new ResourceFoundException(AppConstant.USER_NOT_FOUND);
		}	
		user.get().getUserRole().stream().forEach(ur->{
			System.err.println(ur.getRole().getRoleName()+"????");
		});
		List<GrantedAuthority> authorities = user.get().getUserRole().stream().map(role->new SimpleGrantedAuthority(role.getRole().getRoleName())).collect(Collectors.toList());
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

	@Override
	public ResponseEntity<?> curentUser(Principal p) {
		// TODO Auto-generated method stub
		Map<String,Object> response = new HashMap<>();
		User u = userRepo.findByEmail(p.getName());
		UserResponse userResponse = setData(u);
		response.put(AppConstant.RESPONSE_MESSAGE, userResponse);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	private UserResponse setData(User u) {
		// TODO Auto-generated method stub
		UserResponse userResponse = new UserResponse();
		userResponse.setUserId(u.getUserId());
		userResponse.setFirstName(u.getFirstName());
		userResponse.setLastName(u.getLastName());
		userResponse.setEmail(u.getEmail());
		userResponse.setCreateAt(u.getCreateAt());
		userResponse.setIsActive(u.getIsActive());
		userResponse.setMob(u.getMob());
		userResponse.setProfilePhoto(u.getProfilePhoto());
		userResponse.setTempAddress(u.getTempAddress());
		userResponse.setUserRole(this.userRepo.findRoleNameByEmail(u.getEmail()));
		return userResponse;
	}

	@Override
	public ResponseEntity<?> checkEmail(String email) {
		// TODO Auto-generated method stub
		Map<String, Object>response = new HashMap<>();
	    User u = this.userRepo.findByEmail(email);
	    System.err.println(u);
	    if(u.equals("null")) 
	    {
	    	response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.USER_NOT_FOUND);
	    	return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }
	    response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.USER_IS_VALID);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<?> newPassForForgetPass(String email, String password) {
		// TODO Auto-generated method stub
		Map<String, Object>response = new HashMap<>();
		User user = this.userRepo.findByEmail(email);
		if(user==null) 
		{
			response.put(AppConstant.ERROR, AppConstant.INVALID_USER);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		user.setPassword(pass.encode(password));
		this.userRepo.save(user);
		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.PASSWORD_CHANGE);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@Override
	public ResponseEntity<?> changePassword(ChangePasswordRequest cpr, Principal principal) {
		// TODO Auto-generated method stub
		Map<Object,String> response = new HashMap<>();
		Boolean matches = null;
		if(cpr.getNewPassword().equals(cpr.getVerifyPassword()))
		{
			User user = this.userRepo.findByEmail(principal.getName());
			matches = pass.matches(cpr.getCurrentPassword(), user.getPassword());
			if(matches)
			{
				user.setPassword(pass.encode(cpr.getVerifyPassword()));
				this.userRepo.save(user);
				response.put(matches,AppConstant.PASSWORD_CHANGED);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			else {
				response.put(matches, AppConstant.OLD_PASS_INCORRECT);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
		response.put(false, AppConstant.PASSWORD_NOT_VERIFY);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@Override
	public ResponseEntity<?> updateUser(String updateRequest, String userId, MultipartFile profilePhoto) {
		// TODO Auto-generated method stub
		Map<Object,Object> response = new HashMap<>();
		UserUpdateRequest request=null;
		try {
			request = mapper.readValue(updateRequest, UserUpdateRequest.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		User u = userUpdateReqToUser(request);
		Optional<User> findById = this.userRepo.findById(userId);
		
		if (findById.isPresent() && findById.get().getEmail().equals(u.getEmail())) {
			User user = findById.get();
			u.setUserId(userId);
			u.setCreateAt(user.getCreateAt());
			u.setPassword(user.getPassword());
			u.setUpdateAt(LocalDate.now());
			u.setIsActive(true);
			if(Objects.nonNull(profilePhoto))
			{
				String uploadImage;
				try {
					uploadImage = this.imageService.uploadImage(profilePhoto, FolderName.PROFILE_PHOTO);
					System.err.println(uploadImage);
					u.setProfilePhoto(uploadImage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
			}
			else {
				u.setProfilePhoto(findById.get().getProfilePhoto());
			}
			
			u = this.userRepo.save(u);
			if(Objects.nonNull(u))
			{
				UserResponse userResponse = setData(u);
				response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.USER_UPDATE_SUCCESS);
				response.put(AppConstant.DATA, userResponse);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			else
			{
				response.put(AppConstant.ERROR, AppConstant.USER_UPDATE_FAILED);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		} else {
			response.put(AppConstant.ERROR, AppConstant.CANT_CHANGE_EMAIL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	public User userUpdateReqToUser(UserUpdateRequest uur)
	{
		User user = new User();
		user.setFirstName(uur.getFirstName());
		user.setLastName(uur.getLastName());
		user.setTempAddress(uur.getTempAddress());
		user.setEmail(uur.getEmail());
		user.setMob(uur.getMob());
		return user;
	}
	
	
	
}


