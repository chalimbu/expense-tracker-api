package com.pairlearning.expensetrackerapi.resources;

import java.util.HashMap;
import static java.util.Map.entry;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pairlearning.expensetrackerapi.domain.User;
import com.pairlearning.expensetrackerapi.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserResource {
	
	@Autowired
	UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>> loginUser(@RequestBody Map<String,Object> userMap){
		Optional<String> email = Optional.ofNullable((String) userMap.get("email"));
		Optional<String> password = Optional.ofNullable((String) userMap.get("password"));
		userService.validateUser(email.orElse(""), password.orElse(""));
		Map<String,String> map = Map.ofEntries(
				entry("message","loging succesfully")
				);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<Map<String,String>> registerUser(@RequestBody Map<String, Object> userMap) {
		Optional<String> firstName = Optional.ofNullable((String) userMap.get("firstName"));
		Optional<String> lastName = Optional.ofNullable((String ) userMap.get("lastName"));
		Optional<String> email = Optional.ofNullable((String) userMap.get("email"));
		Optional<String> password = Optional.ofNullable((String) userMap.get("password"));
		userService.registerUser(firstName.orElse(""),lastName.orElse(""),email.orElse(""),password.orElse(""));
		Map<String,String> map = Map.ofEntries(
				entry("message","registerd succesfully")
				);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
