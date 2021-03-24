package com.pairlearning.expensetrackerapi.domain;

public class Category {
    final private Integer cagetoryId;
    final private Integer UserId;
    final private String title;
    final private String description;
    final private Double totalExpense;

    public Category(Integer cagetoryId, Integer userId, String title, String description, Double totalExpense) {
        this.cagetoryId = cagetoryId;
        UserId = userId;
        this.title = title;
        this.description = description;
        this.totalExpense = totalExpense;
    }

    public Integer getCagetoryId() {
        return cagetoryId;
    }

    public Integer getUserId() {
        return UserId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }
}
