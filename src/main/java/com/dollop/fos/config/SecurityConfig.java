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
			"/api/v1/auth/signup",
			"/api/v1/auth/login",
			"/api/v1/otp/checkOTP",
			"/api/v1/emailAPI/sentEmail",
	        };
   private String admin[]= {
		                      "/api/v1/admin/RestaurantApprove/{restId}",
		                      "/api/v1/admin/RestaurantBlock/{restId}",
		                      "/api/v1/admin/RestaurantUnBlock/{restId}",
		                      "/api/v1/admin/all/{pn}/{ps}/{sortBy}",
		                      "/api/v1/admin/Verify/{pn}/{ps}/{sortBy}",
		                      "/api/v1/admin/UnVerify/{pn}/{ps}/{sortBy}",
		                      "/api/v1/admin/UnBlock/{pn}/{ps}/{sortBy}",
		                      "/api/v1/admin/Block/{pn}/{ps}/{sortBy}",
		                      "/api/v1/admin/AllFood/{pn}/{ps}/{sortBy}",
		                      "/api/v1/admin/AllActiveFood/{pn}/{ps}/{sortBy}",
		                      "/api/v1/admin/AllInActiveFood/{pn}/{ps}/{sortBy}",
		                      "/api/v1/globalCategory/save"
                            };
   private String owner[]= {
			              "/api/v1/rest/save",
			              "/api/v1/rest/address/{restId}",
			              "/api/v1/category/save",
			              "/api/v1/category/update",
			              "/api/v1/food/save",

	                       };
 
   private String customer[]= {
		                        "/api/v1/wish/add",
		                        "/api/v1/wish/view",
		                        "/api/v1/wish/remove/{foodId}"
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
