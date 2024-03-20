package com.dollop.fos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	
	@Autowired
	private BCryptPasswordEncoder passwoerEncoder;
	/**For User Details Services*/
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint authenticationEntryPoint;
   @Autowired
	private SecurityFilter securityfilter;
   
   private String permitAll[]= {
		   "/api/v1/review/list-of-review",
			"/api/v1/auth/signup",
			"/api/v1/auth/login",
			"/api/v1/otp/checkOTP",
			"/api/v1/emailAPI/sentEmail",
			"/api/v1/emailAPI/forget-pass",
			 "/api/v1/boy/register",
			 "/api/v1/auth/check/{email}",
			 "/api/v1/auth/new-pass/{email}/{pass}", 
			 "/api/v1/auth/update-user/{userId}",
			 "/api/v1/review/list-of-review-by-restId/{restId}",
			
	        };
   private String owner[]={
           "/api/v1/rest/save",
           "/api/v1/rest/address/{restId}",
           "/api/v1/category/save",
           "/api/v1/category/update",
           "/api/v1/food/save",
           "/api/v1/food/update-food",
           "/api/v1/food/view-food",
           "/api/v1/globalCategory/AllCatName",
           "/api/v1/rest/edit-rest",
           "/api/v1/rest/view-owner-rest",
           "/api/v1/rest/get/{restId}",
           "/api/v1/rest/rest-name-of-owner",
           "/api/v1/globalCategory/cat-name/{restId}",
           "/api/v1/globalCategory/extra-cat-add/{restId}",
           "/api/v1/globalCategory/",
           "/api/v1/rest/cur/{restId}/{status}",
           "/api/v1/rest/{restId}",
           "/api/v1/globalCategory/removeCat/{restId}/{categoryId}"
           
            };
   private String admin[]= {
		                      "/api/v1/admin/RestaurantApprove/{restId}",
		                      "/api/v1/admin/RestaurantBlock/{restId}",
		                      "/api/v1/admin/RestaurantUnBlock/{restId}",
		                      "/api/v1/globalCategory/data",
		                      "/api/v1/globalCategory/save",
		                      "/api/v1/globalCategory/{id}",
		                      "/api/v1/admin/verify",
		                      "/api/v1/admin/all",
		                      "/api/v1/admin/{id}",
		                      "/api/v1/admin/{id}/{status}",
		                      "/api/v1/admin/allRestaurant",
		                      "/api/v1/rest/{id}/{status}",
		                      "/api/v1/admin/allverified",
		                      "/api/v1/rest/dataOfRest",
		                      "/api/v1/admin/verificationData/{id}",
		                      "/api/v1/admin/RestaurantReject/{restId}",
		                      "/api/v1/admin/customer-list",
		                      "/api/v1/admin/owner-list",
		                      "/api/v1/admin/owner-rest/{ownerId}",
		                      "/api/v1/admin/customer-boy-count",
		                      "/api/v1/rest/rest-status-count",

		                      "/api/v1/admin/view-all-food"
            "/api/v1/admin/all-boy-list",
		                      "/api/v1/review/delete-review-by-admin/{reviewId}",

                            };
   
 
   private String customer[]= {
		                        "/api/v1/wish/add",
		                        "/api/v1/wish/view",
		                        "/api/v1/wish/remove/{foodId}",
		                        "/api/v1/review/create-review",
		                        "/api/v1/review/delete-review-by-customer/{reviewId}",
	                          };
   private String boy[]= { 
		                    
                          }; 
   /**For Authentication.....*/
  	@Bean
  	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception 
  	{
  		return authConfig.getAuthenticationManager();
  	}
  	@Bean
  	public DaoAuthenticationProvider authenticationProvider() 
  	{
  		DaoAuthenticationProvider provider  =  new DaoAuthenticationProvider();
  		provider.setPasswordEncoder(passwoerEncoder);
  		provider.setUserDetailsService(userDetailsService);
  		return provider;
  	}
  	@Bean
	public SecurityFilterChain configurePaths(HttpSecurity http) throws Exception 
	{
  		http
		.csrf()
		.disable()
		.authorizeRequests()
		.requestMatchers(permitAll).permitAll()
		.requestMatchers(admin).hasAuthority("ADMIN")
		.requestMatchers(owner).hasAuthority("OWNER")
		.requestMatchers(customer).hasAuthority("CUSTOMER")
		.requestMatchers(boy).hasAuthority("BOY")
		.anyRequest().authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint)
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilterBefore(securityfilter, UsernamePasswordAuthenticationFilter.class);
	
		
		return http.build();
  		
	}
}
