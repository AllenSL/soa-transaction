package com.asl.soatransaction.logic.aop;

import com.alibaba.fastjson.JSONObject;
import com.asl.soatransaction.logic.CacheWrapper;
import com.asl.soatransaction.logic.SOATransactionContext;
import com.asl.soatransaction.logic.domain.SOATransactionFlag;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author ansonglin
 */
@Component
@Aspect
public class SOATransactionAspect {

    private static final Logger LOG = LoggerFactory.getLogger(SOATransactionAspect.class);
    public static final ThreadLocal<SOATransactionFlag> threadLocal = new ThreadLocal<>();
    private CacheWrapper cacheWrapper;
    SOATransactionAspect(CacheWrapper cacheWrapper){
        this.cacheWrapper = cacheWrapper;
    }

    @Before("@annotation(com.asl.soatransaction.annotation.SOATransaction)")
    public void before(){
        //当前线程事务标记
        SOATransactionFlag flag = new SOATransactionFlag();
        threadLocal.set(flag);
    }


    /**
     * 捕获异常，事务回滚
     * @param joinPoint
     */
    @AfterThrowing(value = "@annotation(com.asl.soatransaction.annotation.SOATransaction)")
    public void afterThrowing(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        SOATransactionFlag flag = threadLocal.get();
        if(ObjectUtils.isEmpty(flag)){
            LOG.error("方法:【{}】,事务标记threadLocal为空", methodName);
        }else {
            String txId = flag.getTxId();
            String json = cacheWrapper.get(txId);
            if (StringUtils.isEmpty(json)) {
                LOG.error("方法:【{}】,事务ID:【{}】发生异常，未找到异常回滚方法", methodName, txId);
            } else {
                SOATransactionContext context = JSONObject.parseObject(json, SOATransactionContext.class);
                List<SOATransactionContext.PerServiceContext> contextHolders = context.getContextHolders();
                for (SOATransactionContext.PerServiceContext contextHolder : contextHolders) {
                    try {
                        MethodUtils.invokeMethod(contextHolder.getClz(), contextHolder.getMethodName(), contextHolder.getArgs());
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
