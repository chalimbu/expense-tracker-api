package com.pairlearning.expensetrackerapi.service;

import com.pairlearning.expensetrackerapi.domain.Transaction;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.exeptions.EtResourceNotFoundExeption;

import java.util.List;

public interface TransactionServiceI {

    List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId);

    Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId)
            throws EtResourceNotFoundExeption;

    Transaction addTransaction(Integer userId,Integer categoryId, Double amount, String note, Long transactionDate)
            throws EtBadRequestExeption;

    void updateTransaction(Integer userId,Integer categoryId, Integer transactionId, Transaction transaction)
        throws EtBadRequestExeption;

    void removeTransaction(Integer userId,Integer categoryId, Integer transactionId)
            throws EtResourceNotFoundExeption;
}
