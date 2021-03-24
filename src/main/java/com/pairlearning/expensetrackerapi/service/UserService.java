package com.pairlearning.expensetrackerapi.service;

import com.pairlearning.expensetrackerapi.domain.User;
import com.pairlearning.expensetrackerapi.exeptions.EtAuthExeption;

public interface UserService {

	User validateUser(String email, String password) throws EtAuthExeption;
	
	User registerUser(String firstName, String lastName, String email, String password) throws EtAuthExeption;
	
	
}
