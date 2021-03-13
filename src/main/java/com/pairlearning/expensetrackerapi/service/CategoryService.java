package com.pairlearning.expensetrackerapi.service;

import com.pairlearning.expensetrackerapi.domain.Category;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.exeptions.EtResourceNotFoundExeption;

import java.util.List;

public interface CategoryService {

    List<Category> fetchAllCategorires(final Integer userId);

    Category fetchCategoryById(final Integer userId,final Integer categoryId) throws EtResourceNotFoundExeption;

    Category addCategory(final Integer userId, final String title, final String description) throws EtBadRequestExeption;

    void updateCategory(final Integer userId,final Integer categoryId, final Category category) throws EtBadRequestExeption;

    void removeCategoryWithAllTransactions(final Integer userId,final Integer categoryId) throws EtResourceNotFoundExeption;
}
