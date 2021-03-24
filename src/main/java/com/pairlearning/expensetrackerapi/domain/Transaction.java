package com.pairlearning.expensetrackerapi.domain;

public class Transaction {
    final private Integer transactioId;
    final private Integer categoryId;
    final private Integer userId;
    final private Double amount;
    final private String note;
    final private Long transactionDate;

    public Transaction(Integer transactioId, Integer categoryId, Integer userId, Double amount, String note, Long transactionDate) {
        this.transactioId = transactioId;
        this.categoryId = categoryId;
        this.userId = userId;
        this.amount = amount;
        this.note = note;
        this.transactionDate = transactionDate;
    }

    public Integer getTransactioId() {
        return transactioId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getNote() {
        return note;
    }

    public Long getTransactionDate() {
        return transactionDate;
    }
}
