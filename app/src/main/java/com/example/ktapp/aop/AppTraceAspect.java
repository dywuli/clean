/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.example.ktapp.aop;

import com.android.commonlib.aop.internal.DebugLog;
import com.android.commonlib.aop.internal.StopWatch;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * Aspect representing the cross cutting-concern: Method and Constructor Tracing.
 */
@Aspect
public class AppTraceAspect {

  private static final String POINTCUT_METHOD =
      "execution(@com.example.ktapp.aop.AppTrace * *(..))";

  private static final String POINTCUT_CONSTRUCTOR =
      "execution(@com.example.ktapp.aop.AppTrace *.new(..))";

  @Pointcut(POINTCUT_METHOD)
  public void methodAnnotatedWithAppTrace() {}

  @Pointcut(POINTCUT_CONSTRUCTOR)
  public void constructorAnnotatedAppTrace() {}

  @Around("methodAnnotatedWithAppTrace() || constructorAnnotatedAppTrace()")
  public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    String className = methodSignature.getDeclaringType().getSimpleName();
    String methodName = methodSignature.getName();

    final StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    Object result = joinPoint.proceed();
    stopWatch.stop();

    DebugLog.log(className, buildLogMessage(methodName, stopWatch.getTotalTimeMillis()));

    return result;
  }

  @Before("methodAnnotatedWithAppTrace()")
  public void before(JoinPoint point) {
    System.out.println("AppTraceAspect@Before");
  }
  /**
   * Create a log message.
   *
   * @param methodName A string with the method name.
   * @param methodDuration Duration of the method in milliseconds.
   * @return A string representing message.
   */
  private static String buildLogMessage(String methodName, long methodDuration) {
    StringBuilder message = new StringBuilder();
    message.append("AppTraceAspect --> ");
    message.append(methodName);
    message.append(" --> ");
    message.append("[");
    message.append(methodDuration);
    message.append("ms");
    message.append("]");

    return message.toString();
  }
}
