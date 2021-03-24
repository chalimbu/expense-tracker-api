package com.pairlearning.expensetrackerapi.service;

import com.pairlearning.expensetrackerapi.domain.Transaction;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.exeptions.EtResourceNotFoundExeption;
import com.pairlearning.expensetrackerapi.repositories.TransactionRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionServiceI{

    @Autowired
    TransactionRepositoryI transactionRepositoryI;

    @Override
    public List<Transaction> fetchAllTransactions(Integer userId, Integer categoryId) {
        return transactionRepositoryI.findAll(userId,categoryId);
    }

    @Override
    public Transaction fetchTransactionById(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundExeption {
        return transactionRepositoryI.findById(userId,categoryId,transactionId);
    }

    @Override
    public Transaction addTransaction(final Integer userId,final Integer categoryId,final Double amount,
                                      final String note,final Long transactionDate) throws EtBadRequestExeption {
        final Integer transactionId=transactionRepositoryI.create(userId,categoryId,amount,note,transactionDate);
        return transactionRepositoryI.findById(userId,categoryId,transactionId);
    }

    @Override
    public void updateTransaction(final Integer userId, final Integer categoryId,
                                  final Integer transactionId, final Transaction transaction) throws EtBadRequestExeption {
        transactionRepositoryI.update(userId,categoryId,transactionId,transaction);
    }

    @Override
    public void removeTransaction(Integer userId, Integer categoryId, Integer transactionId) throws EtResourceNotFoundExeption {
        transactionRepositoryI.removeById(userId,categoryId,transactionId);
    }
}
