package com.pairlearning.expensetrackerapi;

import com.pairlearning.expensetrackerapi.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.RegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseTrackerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApiApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean(){
		final AuthFilter authFilter= new AuthFilter();
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>(authFilter);
		registrationBean.addUrlPatterns("/api/categories/*");
		return registrationBean;
	}
}
