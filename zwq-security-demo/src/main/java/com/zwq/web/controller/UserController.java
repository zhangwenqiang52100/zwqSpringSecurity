package com.zwq.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import dto.User;
import dto.UserQueryCondition;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  @GetMapping
  @JsonView(User.UserSimpleView.class)
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


  @GetMapping(value = "/{id:\\d+}")
  @JsonView(User.UserDetailView.class)
  public User getInfo(@PathVariable(value = "id", required = false) String id) {
    User user = new User();
    user.setUsername("tom");
    user.setPassword("1234");
    return user;
  }

}
