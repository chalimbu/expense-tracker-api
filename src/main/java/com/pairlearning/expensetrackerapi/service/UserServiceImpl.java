package com.pairlearning.expensetrackerapi.service;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pairlearning.expensetrackerapi.domain.User;
import com.pairlearning.expensetrackerapi.exeptions.EtAuthExeption;
import com.pairlearning.expensetrackerapi.repositories.UserRepositoryI;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
    UserRepositoryI userRepositoryI;
	
	@Override
	public User validateUser(final String email,final String password) throws EtAuthExeption {
		final String emailLoweCase=email.toLowerCase();
		return userRepositoryI.findByEmailAndPassword(emailLoweCase, password);
	}

	@Override
	public User registerUser(final String firstName,final String lastName,
			final String email,final String password) throws EtAuthExeption {
		final Pattern pattern= Pattern.compile("^(.+)@(.+)$");
		final Optional<String> optionalEmail=Optional.ofNullable(email.toLowerCase());
		if(!pattern.matcher(optionalEmail.orElse("")).matches())
			throw new EtAuthExeption("Invalid email format");
		
		final Integer count= userRepositoryI.getCountByEmail(email);
		if(count > 0) 
			throw new EtAuthExeption("Email already in used");
		
		final Integer userId= userRepositoryI.create(firstName, lastName, email, password);
		
		return userRepositoryI.findById(userId);
	}

}
