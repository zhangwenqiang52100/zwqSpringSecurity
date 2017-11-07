package com.zwq.security.core.properties;

import org.springframework.stereotype.Component;

@Component
public class BrowserProperties {

  private String loginPage = "/zwq-signIn.html";
  private LoginType loginType = LoginType.JSON;

  public String getLoginPage() {
    return loginPage;
  }

  public void setLoginPage(String loginPage) {
    this.loginPage = loginPage;
  }

  public LoginType getLoginType() {
    return loginType;
  }

  public void setLoginType(LoginType loginType) {
    this.loginType = loginType;
  }
}
