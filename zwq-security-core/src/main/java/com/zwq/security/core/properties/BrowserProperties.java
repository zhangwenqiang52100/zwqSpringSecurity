package com.zwq.security.core.properties;

import org.springframework.stereotype.Component;

@Component
public class BrowserProperties {

  private String loginPage = "/zwq-signIn.html";
  private LoginType loginType = LoginType.JSON;
  private  int rememberMeSeconds=3600;



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

  public int getRememberMeSeconds() {
    return rememberMeSeconds;
  }

  public void setRememberMeSeconds(int rememberMeSeconds) {
    this.rememberMeSeconds = rememberMeSeconds;
  }
}
