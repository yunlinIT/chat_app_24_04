package com.koreait.exam.chat_app_24_04.bounded_context.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Value("${custom.jpa.open-in-view.exclude-patterns}")
    private List<String> excludePatterns;

    @Autowired
    private OpenEntityManagerInViewInterceptor openEntityManagerInViewInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(openEntityManagerInViewInterceptor)
                .excludePathPatterns(excludePatterns); // 특정 URL 패턴 제외
    }
}