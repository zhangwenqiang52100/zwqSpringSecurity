package com.zwq.web.controller;

import com.zwq.dto.FileInfo;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

//  private String folder = "E:\\IdeaProjects\\zwqSpringSecurity\\zwq-security-demo\\src\\main\\java\\com\\zwq\\web\\controller";
  private String folder = "D:\\IdeaProjects\\zwq-security\\zwq-security-demo\\src\\main\\java\\com\\zwq\\web\\controller";
  @PostMapping
  public FileInfo upload(MultipartFile file) throws IOException {
    System.out.println(file.getName());
    System.out.println(file.getOriginalFilename());
    System.out.println(file.getSize());
    File localFile = new File(folder, new Date().getTime() + ".txt");
    file.transferTo(localFile);
    return new FileInfo(localFile.getAbsolutePath());
  }

  @GetMapping("/{id}")
  public void download(@PathVariable("id") String id, HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    try (InputStream inputStream = new FileInputStream(new File(folder, id + ".txt"));
        OutputStream outputStream = response.getOutputStream();
    ) {
      response.setContentType("application/x-download");
      response.addHeader("Content-Disposition", "attachment;filename=test.txt");
      IOUtils.copy(inputStream, outputStream);
      outputStream.flush();
    }
  }
}
