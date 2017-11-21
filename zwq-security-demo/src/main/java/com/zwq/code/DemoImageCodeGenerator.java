package com.zwq.code;

import com.zwq.security.core.validate.code.ImageCode;
import com.zwq.security.core.validate.code.ValidateCodeGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator implements ValidateCodeGenerator {

  @Override
  public ImageCode generate(ServletWebRequest request) {
    System.out.println("更高级的图形验证码");
    return null;
  }
}
