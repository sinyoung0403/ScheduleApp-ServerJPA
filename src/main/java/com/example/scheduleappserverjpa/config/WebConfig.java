package com.example.scheduleappserverjpa.config;

import com.example.scheduleappserverjpa.filter.LoginFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.FileFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public FilterRegistrationBean loginFilter() {
    FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

    // 필터 등록
    filterRegistrationBean.setFilter(new LoginFilter());
    // Filter 순서 등록
    filterRegistrationBean.setOrder(1);
    // 전체 URL 에 필터 등록
    filterRegistrationBean.addUrlPatterns("/*");

    return filterRegistrationBean;
  }
}
