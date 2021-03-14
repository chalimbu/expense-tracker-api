package com.pairlearning.expensetrackerapi.resources;

import com.pairlearning.expensetrackerapi.domain.Category;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories(final HttpServletRequest request){
        final Optional<Integer> optUserId = Optional.ofNullable( (Integer) request.getAttribute("userId"));
        return optUserId.map(userId->{
            final List<Category> categories=categoryService.fetchAllCategorires(userId);
            return new ResponseEntity<>(categories,HttpStatus.OK);
        }).orElseThrow(()->{return new EtBadRequestExeption("no able to acces the user id");});
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(HttpServletRequest request,
                                                @RequestBody Map<String,Object> categoryMap){
        final Optional<Integer> optUserId = Optional.ofNullable( (Integer) request.getAttribute("userId"));
        final Optional<String> optTitle = Optional.ofNullable((String) categoryMap.get("title"));
        final Optional<String> optDescription = Optional.ofNullable ((String) categoryMap.get("description"));
        return optUserId.flatMap(userId-> optTitle.flatMap(title -> optDescription.map(description -> {
            final Category category = categoryService.addCategory(userId, title, description);
            return new ResponseEntity<>(category, HttpStatus.CREATED);
        }))).orElseThrow(()->new EtBadRequestExeption("the input doesn't have the needed parameters"));

    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(HttpServletRequest request,
                                                    @PathVariable("categoryId") final Integer categoryId){
        final Optional<Integer> optUserId = Optional.ofNullable( (Integer) request.getAttribute("userId"));
        return optUserId.map(userId->{
            return categoryService.fetchCategoryById(userId,categoryId);
        }).map(category ->{return new ResponseEntity<>(category,HttpStatus.OK);})
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Map<String,Boolean>> updateCategory(HttpServletRequest request,
                                                              @PathVariable("categoryId")final Integer categoryId,
                                                              @RequestBody final Category category){
        final Optional<Integer> optUserId = Optional.ofNullable( (Integer) request.getAttribute("userId"));
        return optUserId.map(userId->{
            categoryService.updateCategory(userId,categoryId,category);
            Map<String,Boolean> objetResult= Map.ofEntries(
                    Map.entry("success",true)
            );
            return new ResponseEntity(objetResult,HttpStatus.OK);
        }).orElseThrow(()-> new EtBadRequestExeption("missing user id in token"));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Map<String,Boolean>> deleteCategory(HttpServletRequest request,
                                                              @PathVariable("categoryId") Integer categoryId){
        final Optional<Integer> optUserId = Optional.ofNullable( (Integer) request.getAttribute("userId"));
        return optUserId.map(userId->{
            categoryService.removeCategoryWithAllTransactions(userId,categoryId);
            return new ResponseEntity<>(Map.ofEntries(Map.entry("succes",true)),HttpStatus.OK);
        }).orElseThrow(()->new EtBadRequestExeption("userId not found"));
    }
}
