package com.task.library_management_system.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.task.library_management_system.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = (authentication != null) ? authentication.getName() : "anonymous";
        log.info("Entering method: {} by user: {}", joinPoint.getSignature().getName(), username);

        var proceed = joinPoint.proceed();

        var executionTime = System.currentTimeMillis() - startTime;

        log.info("Exiting method: {} by user: {} with execution time: {} ms", joinPoint.getSignature().getName(), username, executionTime);

        return proceed;
    }
}
