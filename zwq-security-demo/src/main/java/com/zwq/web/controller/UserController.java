package com.zwq.web.controller;


import com.fasterxml.jackson.annotation.JsonView;
import com.zwq.dto.User;
import com.zwq.dto.UserQueryCondition;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {


  @PostMapping
  @JsonView(User.UserSimpleView.class)
  public User create(@Valid @RequestBody User user, BindingResult erros) {
    if (erros.hasErrors()) {
      erros.getAllErrors().stream().forEach(error -> System.out.println(error.getDefaultMessage()));
    }
    System.out.println(user.getUsername());
    System.out.println(user.getBirthday());
    user.setId("1");
    return user;
  }

  @PutMapping("/{id:\\d+}")
  @JsonView(User.UserSimpleView.class)
  public User update(@Valid @RequestBody User user, BindingResult erros) {
    if (erros.hasErrors()) {
      erros.getAllErrors().stream().forEach(error -> {
        System.out.println(error.getDefaultMessage());
      });
    }
    System.out.println(user.getUsername());
    System.out.println(user.getBirthday());
    user.setId("1");
    return user;
  }

  @DeleteMapping("/{id:\\d+}")
  @JsonView(User.UserSimpleView.class)
  public void delete(@PathVariable(name = "id") String id) {
    System.out.println(id);
  }

  @GetMapping
  @JsonView(User.UserSimpleView.class)
  public List<User> query(UserQueryCondition userQueryCondition,
      @PageableDefault(size = 10, page = 1, sort = {"username,asc"}) Pageable pageable) {
    System.out.println(
        ReflectionToStringBuilder.toString(userQueryCondition, ToStringStyle.MULTI_LINE_STYLE));
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
