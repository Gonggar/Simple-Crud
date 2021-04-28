package edu.bjtu.sei.simplecrud.config;

//import java.time.Duration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.bjtu.sei.simplecrud.controller.intercepter.RateLimitInterceptor;
//import io.github.bucket4j.Bandwidth;
//import io.github.bucket4j.Bucket;
//import io.github.bucket4j.Bucket4j;
//import io.github.bucket4j.Refill;

@Configuration
public class RateLimitConfig implements WebMvcConfigurer {

	  @Override
	  public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(new RateLimitInterceptor()).addPathPatterns("/api/**").addPathPatterns("/api/auth/**");


//	    registry.addInterceptor(new RateLimitInterceptor(bucket, 1))
//	        .addPathPatterns("/place/*");
//
//	    registry.addInterceptor(new PerClientRateLimitInterceptor())
//	        .addPathPatterns("/depth/**");

	  }

	}