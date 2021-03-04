package com.pairlearning.expensetrackerapi.resources;

import java.util.Date;
import static java.util.Map.entry;
import java.util.Map;
import java.util.Optional;

import com.pairlearning.expensetrackerapi.Constants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pairlearning.expensetrackerapi.domain.User;
import com.pairlearning.expensetrackerapi.service.UserService;
import io.jsonwebtoken.impl.DefaultJwtBuilder;

@RestController
@RequestMapping("/api/users")
public class UserResource {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> userMap){
		Optional<String> email = Optional.ofNullable((String) userMap.get("email"));
		Optional<String> password = Optional.ofNullable((String) userMap.get("password"));
		User user=userService.validateUser(email.orElse(""), password.orElse(""));
		Map<String,String> map = generateJWTToken(user);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String, Object> userMap) {
		Optional<String> firstName = Optional.ofNullable((String) userMap.get("firstName"));
		Optional<String> lastName = Optional.ofNullable((String ) userMap.get("lastName"));
		Optional<String> email = Optional.ofNullable((String) userMap.get("email"));
		Optional<String> password = Optional.ofNullable((String) userMap.get("password"));
		User user=userService.registerUser(firstName.orElse(""),lastName.orElse(""),email.orElse(""),password.orElse(""));
		return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
	}

	private Map<String,String> generateJWTToken(final User user){
		final long timestamp= System.currentTimeMillis();
		final String token= Jwts
				.builder()
				.signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
				.setIssuedAt(new Date(timestamp))
				.setExpiration(new Date(timestamp+Constants.TOKEN_VALIDITY))
				.claim("userId",user.getUserId())
				.claim("email",user.getEmail())
				.claim("firstName",user.getFirstName())
				.claim("lastName",user.getLastName())
				.compact();
		return Map.ofEntries(entry("token",token));
	}
}
