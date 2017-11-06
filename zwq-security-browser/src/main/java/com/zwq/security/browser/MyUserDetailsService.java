package com.zwq.security.browser;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Component
public class MyUserDetailsService implements UserDetailsService {

  private Logger logger
      = LoggerFactory.getLogger(getClass());
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.info("登陆用户名:" + username);
    //根据用户名查找用户信息
    //根据查找的信息判断用户是否被冻结

    return new User(username, passwordEncoder.encode("123456"),
        true, true, true, true,
        AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
  }

}
