package com.zwq.web.controller;


import dto.User;
import dto.UserQueryCondition;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  @RequestMapping(value = "/user", method = RequestMethod.GET)
  public List<User> query(UserQueryCondition userQueryCondition,
      @PageableDefault(size = 10, page = 1, sort = {"username,asc"}) Pageable pageable) {
    System.out.println(
        ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));
    System.out.println(pageable.getSort());
    System.out.println(pageable.getSort());
    System.out.println(pageable.getOffset());
    System.out.println(pageable.getPageSize());
    System.out.println(pageable.getPageNumber());
    List<User> userList = new ArrayList<>();
    userList.add(new User());
    userList.add(new User());
    userList.add(new User());
    return userList;
  }

}
