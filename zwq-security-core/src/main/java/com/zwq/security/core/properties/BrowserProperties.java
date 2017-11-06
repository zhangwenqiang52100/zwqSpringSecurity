package com.zwq.security.core.properties;

import org.springframework.stereotype.Component;

@Component
public class BrowserProperties {

  private String loginPage = "/zwq-signIn.html";

  public String getLoginPage() {
    return loginPage;
  }

  public void setLoginPage(String loginPage) {
    this.loginPage = loginPage;
  }
}
