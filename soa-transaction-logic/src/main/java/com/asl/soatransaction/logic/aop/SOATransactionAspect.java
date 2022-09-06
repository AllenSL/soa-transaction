package com.asl.soatransaction.logic.aop;

import com.alibaba.fastjson.JSONObject;
import com.asl.soatransaction.logic.CacheWrapper;
import com.asl.soatransaction.logic.SOATransactionContext;
import com.asl.soatransaction.logic.SOATransactionStatus;
import com.asl.soatransaction.logic.domain.SOATransactionFlag;
import com.asl.soatransaction.logic.exp.SOATransactionException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
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
    private ApplicationContext applicationContext;
    public SOATransactionAspect(CacheWrapper cacheWrapper, ApplicationContext applicationContext){
        this.cacheWrapper = cacheWrapper;
        this.applicationContext = applicationContext;
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
    public void afterThrowing(JoinPoint joinPoint) throws IllegalAccessException {
        String methodName = joinPoint.getSignature().getName();
        SOATransactionFlag flag = threadLocal.get();
        flag.setStatus(SOATransactionStatus.ROLLBACK);
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
                //按顺序依次回滚
                for (int i = contextHolders.size() - 1; i >= 0; i--) {
                    SOATransactionContext.PerServiceContext contextHolder = contextHolders.get(i);
                    try {
                        Object target = applicationContext.getBean(contextHolder.getClz());
                        LOG.debug("SOATransactionContext执行回滚请求目标类target: "+target +" 目标h回滚方法: "+contextHolder.getMethodName()+" 回滚方法入参: "+ contextHolder.getArgs());
                        MethodUtils.invokeMethod(target, contextHolder.getMethodName(), contextHolder.getArgs());
                    } catch (InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                        LOG.error("方法:【{}】,事务ID:【{}】事务回滚失败",methodName, txId,e);
                        throw new SOATransactionException(SOATransactionException.ROLLBACK_EXCEPTION,String.format("方法:【%s】,事务ID:【%s】事务回滚失败",methodName, txId));
                    }
                }
                LOG.info("方法:【{}】,事务ID:【{}】事务回滚成功",methodName, txId);
                this.after();
            }
        }
    }

    @After("@annotation(com.asl.soatransaction.annotation.SOATransaction)")
    private void after() {
        SOATransactionFlag flag = threadLocal.get();
        if(!ObjectUtils.isEmpty(flag)){
            String txId = flag.getTxId();
            cacheWrapper.del(txId);
            threadLocal.remove();
            LOG.info("删除事务txId=【{}】数据成功",txId);
        }
    }
}
