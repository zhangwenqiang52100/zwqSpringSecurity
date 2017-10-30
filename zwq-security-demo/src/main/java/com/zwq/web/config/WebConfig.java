package com.zwq.web.config;

import com.zwq.web.filter.TimeFilter;
import com.zwq.web.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private TimeInterceptor timeInterceptor;

  /**
   * 在这里使用filter中的request和respone无法使用
   */
  @Bean
  public FilterRegistrationBean timeFilter() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    TimeFilter timeFilter = new TimeFilter();
    filterRegistrationBean.setFilter(timeFilter);
    String[] urls = new String[]{"/*"};
    filterRegistrationBean.addUrlPatterns(urls);
    return filterRegistrationBean;
  }

 @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(timeInterceptor);
  }
}
