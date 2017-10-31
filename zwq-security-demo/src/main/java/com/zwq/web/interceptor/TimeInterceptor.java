package com.zwq.web.interceptor;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class TimeInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
    System.out.println(((HandlerMethod) handler).getMethod().getName());
    System.out.println("preHandle");
    request.setAttribute("startTime", new Date().getTime());
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    System.out.println("postHandle");
    long startTime = (long) request.getAttribute("startTime");
    System.out.println("start interceptor 耗时"+(new Date().getTime() - startTime));

  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {
    System.out.println("afterCompletion");
    long startTime = (long) request.getAttribute("startTime");
    System.out.println("time afterCompletion 耗时"+(new Date().getTime() - startTime));
    System.out.println("ex:" + ex);

  }
}
