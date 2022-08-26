package com.asl.soatransaction.logic.aop;

import com.asl.soatransaction.logic.domain.SOATransactionFlag;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author ansonglin
 */
@Component
@Aspect
public class SOATransactionAspect {

    private static final Logger LOG = LoggerFactory.getLogger(SOATransactionAspect.class);
    public static final ThreadLocal<SOATransactionFlag> threadLocal = new ThreadLocal<>();

    @Before("@annotation(com.asl.soatransaction.annotation.SOATransaction)")
    public void before(){
        //当前线程事务标记
        SOATransactionFlag flag = new SOATransactionFlag();
        threadLocal.set(flag);
    }


    /**
     * 捕获异常，事务回滚
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "@annotation(com.asl.soatransaction.annotation.SOATransaction)",throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e){
        String methodName = joinPoint.getSignature().getName();
        SOATransactionFlag flag = threadLocal.get();

    }

}
