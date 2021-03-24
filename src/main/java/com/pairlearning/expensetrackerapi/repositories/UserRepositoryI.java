package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.domain.User;
import com.pairlearning.expensetrackerapi.exeptions.EtAuthExeption;

public interface UserRepositoryI {

	Integer create(String firstName, String lastName, String email, String password) throws EtAuthExeption;
	
	User findByEmailAndPassword(String email, String password) throws EtAuthExeption;
	
	Integer getCountByEmail(String email);
	
	User findById(Integer userId);
}
