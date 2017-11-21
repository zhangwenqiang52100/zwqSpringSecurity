package com.zwq.security.browser;

import com.zwq.security.browser.authentication.ZwqAuthenticationFailHandler;
import com.zwq.security.browser.authentication.ZwqAuthenticationSuccessHandler;
import com.zwq.security.core.properties.SecurityProperties;
import com.zwq.security.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private SecurityProperties securityProperties;
  @Autowired
  @Qualifier("zwqAuthenticationSuccessHandler")
  private ZwqAuthenticationSuccessHandler zwqAuthenticationSuccessHandler;
  @Autowired
  @Qualifier("zwqAuthenticationFailHandler")
  private ZwqAuthenticationFailHandler zwqAuthenticationFailHandler;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }



  @Override
  protected void configure(HttpSecurity http) throws Exception {
    ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
    validateCodeFilter.setAuthenticationFailureHandler(zwqAuthenticationFailHandler);
    http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin()
        .loginPage("/authentication/require")
        .loginProcessingUrl("/authentication/form")
        .successHandler(zwqAuthenticationSuccessHandler)
        .failureHandler(zwqAuthenticationFailHandler)
        .and()
        .authorizeRequests()
        .antMatchers("/authentication/require",
            securityProperties.getBrowser().getLoginPage(),"/code/image").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf().disable();
  }
}
