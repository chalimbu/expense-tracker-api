package com.pairlearning.expensetrackerapi.repositories;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.pairlearning.expensetrackerapi.domain.User;
import com.pairlearning.expensetrackerapi.exeptions.EtAuthExeption;

@Repository
public class UserRepositoryIImpl implements UserRepositoryI {
	
	final private static String SQL_CREATE="INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, "
			+ "PASSWORD) VALUES (NEXTVAL('ET_USER_SEG'), ?,?,?,?)";
	
	final private static String SQL_COUNT_BY_EMAIL="SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
	final private static String SQL_FIND_BY_ID="SELECT USER_ID, FIRST_NAME , LAST_NAME, EMAIL, PASSWORD FROM ET_USERS"
			+ " WHERE USER_ID = ?";
	final private static String SQL_FIND_BY_EMAIL="SELECT USER_ID, FIRST_NAME , LAST_NAME, EMAIL, PASSWORD FROM ET_USERS"
			+ " WHERE EMAIL = ?";

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public Integer create(final String firstName,final String lastName,
			final String email, String password) throws EtAuthExeption {
		final String hashedPassworD = BCrypt.hashpw(password,BCrypt.gensalt(10));
		try {
			KeyHolder keyHolder= new GeneratedKeyHolder();
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, email);
				ps.setString(4, hashedPassworD);
				return ps;
			},keyHolder);
			return (Integer) keyHolder.getKeys().get("USER_ID");
		}catch(Exception e) {
			throw new EtAuthExeption("Invalid details. Failed to create"+ e.getMessage());
		} 
	}

	@Override
	public User findByEmailAndPassword(final String email,final String password) throws EtAuthExeption {
		try {
			User user= jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL,new Object[]{email},userRowMapper);
			if(!BCrypt.checkpw(password, user.getPassword()))
				throw new EtAuthExeption("Invalid email/password");
			return user;
		}catch(Exception e) {
			throw new EtAuthExeption("Invalid email/password");
		}
	}

	@Override
	public Integer getCountByEmail(final String email) {
		//original jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,new Object[]{email}Integer.class); but deprecate 
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,Integer.class,new Object[]{email});
	}

	@Override
	public User findById(final Integer userId) {
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{userId},userRowMapper);
	}
	
	static private RowMapper<User> userRowMapper = ((rs,rowNum)->{
		return new User(rs.getInt("USER_ID"),
				rs.getString("FIRST_NAME"),
				rs.getString("LAST_NAME"),
				rs.getString("EMAIL"),
				rs.getString("PASSWORD"));
	});

}
