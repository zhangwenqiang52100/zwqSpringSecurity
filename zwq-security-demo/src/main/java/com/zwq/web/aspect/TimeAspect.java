package com.zwq.web.aspect;

import java.util.Arrays;
import java.util.Date;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeAspect {

  @Around("execution(* com.zwq.web.controller.UserController.*(..))")
  public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    System.out.println("time aspect start");
    Object[] args = proceedingJoinPoint.getArgs();
    Arrays.stream(args).forEach(arg -> System.out.println(arg));
    long startTime = new Date().getTime();
    Object object = proceedingJoinPoint.proceed();
    System.out.println("start aspect 耗时" + (startTime - startTime));
    System.out.println("time aspect end");
    return object;
  }

}
