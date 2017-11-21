package com.zwq.security.core.validate.code;

import com.zwq.security.core.properties.SecurityProperties;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class ValidateCodeCotroller {

  public static final String SESSION_KEY = "SESSION_KEY_IMSGE_CODE";

  private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

  @Autowired
  private ValidateCodeGenerator imageCodeGenerator;

  @GetMapping("/code/image")
  public void createCode(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    ImageCode imageCode = imageCodeGenerator.generate(new ServletWebRequest(request));
    sessionStrategy.setAttribute(new ServletRequestAttributes(request), SESSION_KEY, imageCode);
    ImageIO.write(imageCode.getImage(), "jpeg", response.getOutputStream());
  }
}
