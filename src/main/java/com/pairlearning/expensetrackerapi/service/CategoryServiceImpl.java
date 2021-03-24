package com.pairlearning.expensetrackerapi.service;

import com.pairlearning.expensetrackerapi.domain.Category;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.exeptions.EtResourceNotFoundExeption;
import com.pairlearning.expensetrackerapi.repositories.CategoryRepositoryI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    CategoryRepositoryI categoryRepositoryI;

    @Override
    public List<Category> fetchAllCategorires(Integer userId) {
        return categoryRepositoryI.findAll(userId);
    }

    @Override
    public Category fetchCategoryById(Integer userId, Integer categoryId)
            throws EtResourceNotFoundExeption {
        return categoryRepositoryI.findById(userId,categoryId);
    }

    public Category addCategory(final Integer userId,final String title,final String description) throws EtBadRequestExeption {
        final Integer categoryId= categoryRepositoryI.create(userId,title,description);
        return categoryRepositoryI.findById(userId,categoryId);
    }

    @Override
    public void updateCategory(Integer userId, Integer categoryId, Category category) throws EtBadRequestExeption {
        categoryRepositoryI.update(userId,categoryId,category);
    }

    @Override
    public void removeCategoryWithAllTransactions(Integer userId, Integer categoryId) throws EtResourceNotFoundExeption {
        this.fetchCategoryById(userId,categoryId);
        categoryRepositoryI.removeById(userId,categoryId);
    }
}
