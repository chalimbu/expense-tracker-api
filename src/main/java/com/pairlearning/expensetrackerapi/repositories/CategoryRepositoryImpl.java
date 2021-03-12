package com.pairlearning.expensetrackerapi.repositories;

import com.pairlearning.expensetrackerapi.domain.Category;
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
import java.util.concurrent.ExecutionException;

@Repository
public class CategoryRepositoryImpl implements CategoryRepositoryI{

    final private static String SQL_CREATE="INSERT INTO ET_CATEGORIES (CATEGORY_ID, USER_ID, TITLE, DESCRIPTION) " +
            "VALUES (NEXTVAL('ET_CATEGORIES_SEQ'), ?, ?, ?)";

    final private static String SQL_FIND_BY_ID = "SELECT C.CATEGORY_ID, C.USER_ID, C.TITLE, C.DESCRIPTION," +
            "COALESCE(SUM(T.AMOUNT), 0) TOTAL_EXPENSE " +
            "FROM ET_TRANSACTIONS T RIGHT OUTER JOIN ET_CATEGORIES C ON C.CATEGORY_ID = T.CATEGORY_ID " +
            "WHERE C.USER_ID= ? AND C.CATEGORY_ID = ? GROUP BY C.CATEGORY_ID";

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Category> findAll(Integer userId) throws EtResourceNotFoundExeption {
        return null;
    }

    public Category findById(final Integer userId, final Integer categoryId) throws EtResourceNotFoundExeption {
        try{
            return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId, categoryId},categoryRowMapper);
        }catch (Exception e){
            System.out.println(e);
            throw new EtResourceNotFoundExeption("Category not found");
        }
    }

    public Integer create(Integer userId, String title, String description) throws EtBadRequestExeption {
        try{
            KeyHolder keyHolder= new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps= connection. prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,userId);
                ps.setString(2,title);
                ps.setString(3,description);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("CATEGORY_ID");
        }catch (Exception e){
            throw new EtBadRequestExeption("Invalid request");
        }
    }

    public void update(Integer userId, Integer categoryId, Category category) throws EtBadRequestExeption {

    }

    public void removeById(Integer userId, Integer categoryId) {

    }

    private RowMapper<Category> categoryRowMapper= (((resultSet, i) -> {
        return new Category(resultSet.getInt("CATEGORY_ID"),
                resultSet.getInt("USER_ID"),
                resultSet.getString("TITLE"),
                resultSet.getString("DESCRIPTION"),
                resultSet.getDouble("TOTAL_EXPENSE"));
    }));
}
