package com.zwq.web.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import jdk.jfr.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void whenQuerySuccess() throws Exception {
    String result = mockMvc
        .perform(
            get("/user").param("username", "jojo").param("age", "14")
                .param("ageTo", "17").param("xxx", "qwe")
                //    .param("size", "13").param("sort", "age,desc").param("page", "3")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(3)).andReturn().getResponse().getContentAsString();

    System.out.println(result);
  }


  @Test
  public void whenGeTUserInfoSuccess() throws Exception {

    String result = mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk()).andExpect(jsonPath("$.username").value("tom")).andReturn()
        .getResponse().getContentAsString();

    System.out.println(result);
  }

  @Test
  public void whenGetUserInfoFail() throws Exception {

    mockMvc.perform(get("/user/is").contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().is4xxClientError());
  }


  @Test
  public void whenCreateSuccess() throws Exception {
    String content = "{\n"
        + "  \"username\": \"tom\",\n"
        + "  \"password\": null,\n"
        + " \"birthday\":" + new Date().getTime()
        + "\n}";
    String result = mockMvc
        .perform(post("/user").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1")).andReturn().getResponse()
        .getContentAsString();
    System.out.println(result);
  }

  @Test
  public void whenUpdateSuccess() throws Exception {

    Date date = new Date(
        LocalDateTime.now().plusYears(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    String content = "{\n"
        + "  \"id\": 1,\n"
        + "  \"username\": \"tom\",\n"
        + "  \"password\": \"1234\",\n"
        + "   \"birthday\":" + date.getTime()
        + "\n}";
    String result = mockMvc
        .perform(put("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8).content(content))
        .andExpect(status().isOk()).andExpect(jsonPath("$.id").value("1")).andReturn().getResponse()
        .getContentAsString();
    System.out.println(result);
  }

  @Test
  public void whenDeleteSuccess() throws Exception {
    mockMvc
        .perform(delete("/user/1").contentType(MediaType.APPLICATION_JSON_UTF8));
  }

  @Test
  public void whenUploadSuccess() throws Exception {
    String result = mockMvc.perform(fileUpload("/file").file(
        new MockMultipartFile("file", "test.txt", "multipart/form-data",
            "hekko word".getBytes("utf-8"))))
        .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    System.out.println(result);
  }

}
