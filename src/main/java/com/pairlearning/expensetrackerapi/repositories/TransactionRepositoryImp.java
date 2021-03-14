package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.domain.Category;
import com.pairlearning.expensetrackerapi.domain.Transaction;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.exeptions.EtResourceNotFoundExeption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TransactionRepositoryImp implements TransactionRepositoryI{

    final private static String SQL_CREATE="INSERT INTO ET_TRANSACTIONS (TRANSACTION_ID, CATEGORY_ID, USER_ID, " +
            "AMOUNT, NOTE, TRANSACTION_DATE) VALUES(NEXTVAL('et_transactions_seq'), ?, ?, ?, ?, ? )";

    final private static String SQL_FIND_BY_ID="SELECT TRANSACTION_ID, CATEGORY_ID, USER_ID, AMOUNT, NOTE," +
            "TRANSACTION_DATE FROM ET_TRANSACTIONS WHERE USER_ID = ? AND CATEGORY_ID = ? AND TRANSACTION_ID = ? ";

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Transaction> findAll(Integer userId, Integer categoryId) {
        return null;
    }

    @Override
    public Transaction findById(final Integer userId, final Integer categoryId,
                                final Integer transactionId) throws EtResourceNotFoundExeption {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId,transactionId},transactionRowMapper);
        }catch (Exception e){
            throw new EtResourceNotFoundExeption("Transaction not found");
        }
    }

    @Override
    public Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate) throws EtBadRequestExeption {
        try{
            KeyHolder keyHolder= new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps= connection. prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,categoryId);
                ps.setInt(2,userId);
                ps.setDouble(3,amount);
                ps.setString(4,note);
                ps.setLong(5,transactionDate);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("TRANSACTION_ID");
        }catch (Exception e){
            System.out.println(e);
            throw new EtBadRequestExeption("Invalid request");
        }
    }

    @Override
    public void update(Integer userId, Integer categoryId, Integer transactionId, Transaction transaction) throws EtBadRequestExeption {

    }

    @Override
    public void removeById(Integer userId, Integer categoryId, Integer transactionId) throws EtBadRequestExeption {

    }

    private RowMapper<Transaction> transactionRowMapper= (((resultSet, i) -> {
        return new Transaction(
                resultSet.getInt("TRANSACTION_ID"),
                resultSet.getInt("CATEGORY_ID"),
                resultSet.getInt("USER_ID"),
                resultSet.getDouble("AMOUNT"),
                resultSet.getString("NOTE"),
                resultSet.getLong("TRANSACTION_DATE")
                );
    }));
}
