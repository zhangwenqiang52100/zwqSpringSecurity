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

  public BrowserProperties getBrowser() {
    return browser;
  }

  public void setBrowser(BrowserProperties browser) {
    this.browser = browser;
  }
}
