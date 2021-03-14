package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.domain.Transaction;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.exeptions.EtResourceNotFoundExeption;

import java.util.List;

public interface TransactionRepositoryI {
    List<Transaction> findAll(Integer userId, Integer categoryId);

    Transaction findById(Integer userId, Integer categoryId,Integer transactionId)
            throws EtResourceNotFoundExeption;

    Integer create(Integer userId, Integer categoryId, Double amount, String note, Long transactionDate)
            throws EtBadRequestExeption;

    void update(Integer userId, Integer categoryId,Integer transactionId, Transaction transaction)
            throws EtBadRequestExeption;

    void removeById(Integer userId, Integer categoryId,Integer transactionId) throws EtBadRequestExeption;
}
