package com.github.shivanikanojia.monitor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@EnableAspectJAutoProxy
public class LoggerMonitor {

	private static final Logger log = LoggerFactory.getLogger(LoggerMonitor.class);

	@Pointcut("@annotation(com.github.shivanikanojia.annotation.Logging) "
			+ "|| @target(com.github.shivanikanojia.annotation.Logging)")
	public void methodOrClassLoggingEnabledPointcut() {
	}

	@Pointcut("within(@org.springframework.web.bind.annotation.RestController *) ||"
			+ "within(@org.springframework.stereotype.Controller *)")
	public void allPublicControllerMethodsPointcut() {
	}

	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void allPublicServiceMethodsPointcut() {
	}

//	@Around("execution(* com..controller..*(..)) || execution(* com..service..*(..))")
	@Around("methodOrClassLoggingEnabledPointcut() && (allPublicControllerMethodsPointcut() || allPublicServiceMethodsPointcut())")
	public Object logService(ProceedingJoinPoint jointPoint) throws Throwable {

		Long start = System.currentTimeMillis();
		log.info("===============Started {} on {} ms ============", jointPoint, start);
		Object jp = jointPoint.proceed();
		Long end = System.currentTimeMillis();
		log.info("==================Completed {} on {} ms =================", jointPoint, end);
		log.info("=================={} ran for {} ms =================", jointPoint, end - start);
		return jp;
	}
	
	@AfterReturning("methodOrClassLoggingEnabledPointcut() && (allPublicControllerMethodsPointcut() || allPublicServiceMethodsPointcut())")
	public void logExceptions(JoinPoint joinPoint) throws Throwable {
		Long start = System.currentTimeMillis();
		log.info("===============Ecxeption occurred in {} on {} ms ============", joinPoint, start);
	}
}
