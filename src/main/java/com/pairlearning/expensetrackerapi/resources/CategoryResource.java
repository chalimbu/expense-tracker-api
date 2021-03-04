package com.pairlearning.expensetrackerapi.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/categories")
public class CategoryResource {

    @GetMapping("")
    public String getAllCategories(final HttpServletRequest request){
        final Integer userId = (Integer) request.getAttribute("userId");
        return "Authenticated Userid: " + userId;
    }
}
