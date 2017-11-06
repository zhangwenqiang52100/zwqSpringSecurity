package com.zwq.security.browser;

import com.zwq.security.browser.authentication.ZwqAuthenticationFailHandler;
import com.zwq.security.browser.authentication.ZwqAuthenticationSuccessHandler;
import com.zwq.security.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    http.formLogin()
        .loginPage("/authentication/require")
        .loginProcessingUrl("/authentication/form")
        .successHandler(zwqAuthenticationSuccessHandler)
        .failureHandler(zwqAuthenticationFailHandler)
        .and()
        .authorizeRequests()
        .antMatchers("/authentication/require",
            securityProperties.getBrowser().getLoginPage()).permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf().disable();
  }
}
