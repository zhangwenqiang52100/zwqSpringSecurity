package com.zwq.security.core.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "zwq.security")
public class SecurityProperties {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Autowired
  private BrowserProperties browser;
  private ValidateCodeProperties code = new ValidateCodeProperties();

  public BrowserProperties getBrowser() {
    return browser;
  }

  public void setBrowser(BrowserProperties browser) {
    this.browser = browser;
  }

  public ValidateCodeProperties getCode() {
    return code;
  }

  public void setCode(ValidateCodeProperties code) {
    this.code = code;
  }
}
