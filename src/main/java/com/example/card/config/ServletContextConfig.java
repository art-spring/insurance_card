package com.example.card.config;

import com.example.InsuranceCardApplication;
import com.example.card.interceptor.LoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * Created by caichunyi on 2017/4/5.
 */
@Configuration
@ComponentScan(basePackageClasses = InsuranceCardApplication.class, useDefaultFilters = true)
public class ServletContextConfig extends WebMvcConfigurationSupport {

    private static Logger logger = LoggerFactory.getLogger(ServletContextConfig.class);

    private static final String FAVICON_URL = "/favicon.ico";
    private static final String PROPERTY_APP_ENV = "application.environment";
    private static final String PROPERTY_DEFAULT_ENV = "dev";

//    @Override
//    protected void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(FAVICON_URL);
//    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/").addResourceLocations("/**");
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    protected void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}
