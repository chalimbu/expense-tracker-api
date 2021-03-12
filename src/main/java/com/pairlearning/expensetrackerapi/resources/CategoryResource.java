package com.pairlearning.expensetrackerapi.resources;

import com.pairlearning.expensetrackerapi.domain.Category;
import com.pairlearning.expensetrackerapi.exeptions.EtBadRequestExeption;
import com.pairlearning.expensetrackerapi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    @Autowired
    CategoryService categoryService;

    @GetMapping("")
    public String getAllCategories(final HttpServletRequest request){
        final Integer userId = (Integer) request.getAttribute("userId");
        return "Authenticated Userid: " + userId;
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
}
