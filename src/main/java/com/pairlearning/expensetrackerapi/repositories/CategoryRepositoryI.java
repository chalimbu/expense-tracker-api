package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.domain.Category;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.exeptions.EtResourceNotFoundExeption;

import java.util.List;

public interface CategoryRepositoryI {

    List<Category> findAll(final Integer userId) throws EtResourceNotFoundExeption;

    Category findById(final Integer userId,final Integer categoryId) throws EtResourceNotFoundExeption;

    Integer create(final Integer userId,final String title, final String description) throws EtBadRequestExeption;

    void update(final Integer userId,final Integer categoryId,final Category category) throws EtBadRequestExeption;

    void removeById(final Integer userId, final Integer categoryId);
}
