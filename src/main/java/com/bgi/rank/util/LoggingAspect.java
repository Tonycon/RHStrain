package com.bgi.rank.util;

import org.apache.log4j.Logger;  
import org.aspectj.lang.ProceedingJoinPoint;  
import org.aspectj.lang.annotation.Around;  
import org.aspectj.lang.annotation.Aspect;  
import org.springframework.stereotype.Component;  
  
/** 
 *  使用Aspect统计方法调用的时间 
 * @author dufy 
 * @Date 2016-03-02 
 * @version 1.0 
 * 
 */  
  
@Component  
@Aspect  
public class LoggingAspect {  
    //日志记录  
    public Logger log = Logger.getLogger("reqTime_logger");  
      
    /** 
     * 统计Service中方法调用的时间 
     * @param joinPoint 
     * @throws Throwable 
     */  
    @Around("execution(* com.bgi..*Service.*(..))")  
    public Object logServiceMethodAccess(ProceedingJoinPoint thisJoinPoint) throws Throwable {  
    	String clazzName = thisJoinPoint.getTarget().getClass().getName();  
        String methodName = thisJoinPoint.getSignature().getName();  
          
        // 计时并调用目标函数  
        long start = System.currentTimeMillis();  
        Object result = thisJoinPoint.proceed();  
        long time = System.currentTimeMillis() - start;  
           
        // 输出计时信息  
        log.info("操作计时：" + time + " ms,  类名: " + clazzName+ "  方法名:" + methodName + "()");  
        return result;  
    }  
}  