package com.dollop.fos.utility;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dollop.fos.helper.AppConstant;
import com.dollop.fos.reposatory.IUserRepo;
import com.dollop.fos.service.IUserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtUtils {
	
	
	@Autowired
	private IUserRepo urepo;
	
	@Autowired
	private IUserService service;	

    private String SECRET_KEY = "SheetalPatidarBscComputerScience3yearABCDEFGHIGHIJKLMNOPQRSTUVWXYZSheetalPatidarBscComputerScience3yearABCDEFGHIGHIJKLMNOPQRSTUVWXYZ";
  

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public ResponseEntity<?> generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String userRole = this.service.getUserRole(userName);
        String token;
        if(userRole.equalsIgnoreCase("BOY"))
        {
        	if(this.urepo.findIsActiveByEmail(userName))
        	{
        		token = createToken(claims, userName);
        		response.put(AppConstant.TOKEN, token);
        	}else {
        		response.put(AppConstant.RESPONSE_MESSAGE, AppConstant.WAIT_FOR_APPROVAL_BOY);
        	}
        }
        else {
        	token = createToken(claims, userName);
        	response.put(AppConstant.TOKEN, token);
        }
        response.put(AppConstant.ROLE, userRole);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
