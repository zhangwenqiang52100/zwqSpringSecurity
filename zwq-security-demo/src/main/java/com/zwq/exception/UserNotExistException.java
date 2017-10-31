package com.zwq.exception;

import org.springframework.data.annotation.Id;

public class UserNotExistException extends RuntimeException {

  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public UserNotExistException(String id) {
    super("user not exist");
    this.id = id;
  }
}
