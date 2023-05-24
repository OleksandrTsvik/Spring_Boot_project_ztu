package ztu.education.spring_boot_web_project.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    @Around("execution(* ztu.education.spring_boot_web_project.repository.*.*(..))")
    public Object aroundAllRepositoryMethodsAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName();

        System.out.println("\u001B[32m" + "Begin of " + methodName + "\u001B[0m");
        Object targetMethodResult = proceedingJoinPoint.proceed();
        System.out.println("\u001B[34m" + "End of " + methodName + "\u001B[0m");

        return targetMethodResult;
    }
}