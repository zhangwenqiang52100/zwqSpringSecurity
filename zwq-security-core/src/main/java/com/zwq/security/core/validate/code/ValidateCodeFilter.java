package com.zwq.security.core.validate.code;

import com.zwq.security.core.properties.SecurityProperties;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {


  @Autowired
  private AuthenticationFailureHandler authenticationFailureHandler;

  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
  private Set<String> urls = new HashSet<>();
  private SecurityProperties securityProperties;
  private AntPathMatcher antPathMatcher = new AntPathMatcher();

  @Override
  public void afterPropertiesSet() throws ServletException {
    super.afterPropertiesSet();
    String[] configUrls = StringUtils
        .splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getImage().getUrl(),
            ",");
 /*   for (String configUrl : configUrls) {
      urls.add(configUrl);
    }
*/
    urls.add("/authentication/form");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    boolean action = false;
    for (String url : urls) {
      if (antPathMatcher.match(url, request.getRequestURI())) {
        action = true;
      }
    }
    if (action) {
      try {
        validate(new ServletWebRequest(request));
      } catch (ValidateCodeException exception) {
        authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
        return;
      }
    }
    filterChain.doFilter(request, response);
  }

  private void validate(ServletWebRequest request) throws ValidateCodeException {
    ImageCode codeInSession = (ImageCode) sessionStrategy
        .getAttribute(request, ValidateCodeCotroller.SESSION_KEY);

    String codeInRequest;
    try {
      codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
          "imageCode");
    } catch (ServletRequestBindingException e) {
      throw new ValidateCodeException("获取验证码的值失败");
    }

    if (StringUtils.isBlank(codeInRequest)) {
      throw new ValidateCodeException("验证码的值不能为空");
    }

    if (codeInSession == null) {
      throw new ValidateCodeException("验证码不存在");
    }

    if (codeInSession.isExpried()) {
      sessionStrategy.removeAttribute(request, ValidateCodeCotroller.SESSION_KEY);
      throw new ValidateCodeException("验证码已过期");
    }

    if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
      throw new ValidateCodeException("验证码不匹配");
    }

    sessionStrategy.removeAttribute(request, ValidateCodeCotroller.SESSION_KEY);
  }

  public AuthenticationFailureHandler getAuthenticationFailureHandler() {
    return authenticationFailureHandler;
  }

  public void setAuthenticationFailureHandler(
      AuthenticationFailureHandler authenticationFailureHandler) {
    this.authenticationFailureHandler = authenticationFailureHandler;
  }

  public SecurityProperties getSecurityProperties() {
    return securityProperties;
  }

  public void setSecurityProperties(SecurityProperties securityProperties) {
    this.securityProperties = securityProperties;
  }
}
