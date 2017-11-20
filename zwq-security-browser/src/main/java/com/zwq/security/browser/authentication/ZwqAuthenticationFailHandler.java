package com.zwq.security.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwq.security.browser.support.SimpleResponse;
import com.zwq.security.core.properties.LoginType;
import com.zwq.security.core.properties.SecurityProperties;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;


@Component("zwqAuthenticationFailHandler")
public class ZwqAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private SecurityProperties securityProperties;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {
    logger.info("登录失败");
    if (LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setContentType("application/json;chartset=utf-8");
      response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
    } else {
      super.onAuthenticationFailure(request, response, exception);
    }

  }
}
