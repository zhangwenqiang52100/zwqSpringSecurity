package com.zwq.web.controller;

import com.zwq.dto.FileInfo;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

  @PostMapping
  public FileInfo upload(MultipartFile file) throws IOException {
    System.out.println(file.getName());
    System.out.println(file.getOriginalFilename());
    System.out.println(file.getSize());
    String folder = "E:\\IdeaProjects\\zwqSpringSecurity\\zwq-security-demo\\src\\main\\java\\com\\zwq\\web\\controller";
    File localFile = new File(folder, new Date().getTime() + ".txt");
    file.transferTo(localFile);
    return new FileInfo(localFile.getAbsolutePath());
  }
}
