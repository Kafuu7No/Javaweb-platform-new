package com.example.qna.config;

import com.example.qna.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/index", "/login", "/register", "/captcha", "/css/**", "/js/**", "/images/**");
    }

    // 静态资源映射通常不需要额外配置，Spring Boot 默认 /static 映射为根路径。
}
