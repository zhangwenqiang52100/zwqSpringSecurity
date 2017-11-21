package com.zwq.security.browser;

import com.zwq.security.browser.authentication.ZwqAuthenticationFailHandler;
import com.zwq.security.browser.authentication.ZwqAuthenticationSuccessHandler;
import com.zwq.security.core.properties.SecurityProperties;
import com.zwq.security.core.validate.code.ValidateCodeFilter;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  @Qualifier("dataSource")
  private DataSource dataSource;
  @Autowired
  private UserDetailsService userDetailsService;
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

  @Bean
  public PersistentTokenRepository persistentTokenRepository() {
    JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
    tokenRepository.setDataSource(dataSource);
   tokenRepository.setCreateTableOnStartup(true);
    return tokenRepository;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
    validateCodeFilter.setAuthenticationFailureHandler(zwqAuthenticationFailHandler);
    validateCodeFilter.setSecurityProperties(securityProperties);
    validateCodeFilter.afterPropertiesSet();
    http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
        .formLogin()
        .loginPage("/authentication/require")
        .loginProcessingUrl("/authentication/form")
        .successHandler(zwqAuthenticationSuccessHandler)
        .failureHandler(zwqAuthenticationFailHandler)
        .and().rememberMe().tokenRepository(persistentTokenRepository())
        .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds()).
        userDetailsService(userDetailsService)
        .and()
        .authorizeRequests()
        .antMatchers("/authentication/require",
            securityProperties.getBrowser().getLoginPage(), "/code/image").permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .csrf().disable();
  }
}
