package com.zwq.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwq.security.core.properties.LoginType;
import com.zwq.security.core.properties.SecurityProperties;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


@Component("zwqAuthenticationSuccessHandler")
public class ZwqAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    logger.info("登录成功");
    if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
      response.setContentType("application/json;chartset=utf-8");
      response.getWriter().write(objectMapper.writeValueAsString(authentication));
    } else {
      super.onAuthenticationSuccess(request,response,authentication);
    }
  }
}
