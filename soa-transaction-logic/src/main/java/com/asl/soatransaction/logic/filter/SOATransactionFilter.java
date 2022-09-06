package com.asl.soatransaction.logic.filter;

import com.alibaba.fastjson.JSONObject;
import com.asl.soatransaction.logic.CacheWrapper;
import com.asl.soatransaction.logic.SOARollbackMeta;
import com.asl.soatransaction.logic.SOATransactionContext;
import com.asl.soatransaction.logic.SOATransactionStatus;
import com.asl.soatransaction.logic.aop.SOATransactionAspect;
import com.asl.soatransaction.logic.domain.SOATransactionFlag;
import com.asl.soatransaction.logic.exp.SOATransactionException;
import com.asl.soatransaction.logic.spring.SOATransactionBeanProcessor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * SOATransactionFilter
 * @author ansonglin
 */
@Activate(group = {CommonConstants.CONSUMER,CommonConstants.PROVIDER})
public class SOATransactionFilter implements Filter {
      Logger LOG =  LoggerFactory.getLogger(SOATransactionFilter.class);
    /**
     * 过期时间 60s*60*24=24h
     */
    public static int EXPIRE_TIME = 86400;

    private CacheWrapper cacheWrapper;

    public void setCacheWrapper(CacheWrapper cacheWrapper) {
        this.cacheWrapper = cacheWrapper;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        SOATransactionFlag flag = SOATransactionAspect.threadLocal.get();
        if(!ObjectUtils.isEmpty(flag) && flag.getStatus() == SOATransactionStatus.START ) {
            String txId = flag.getTxId();
            Class<?> clz = invoker.getInterface();
            String methodName = invocation.getMethodName();
            Object[] arguments = invocation.getArguments();
            Class<?>[] parameterTypes = invocation.getParameterTypes();
            Method method = MethodUtils.getAccessibleMethod(clz, methodName, invocation.getParameterTypes());
//            if(!method.isAnnotationPresent(SOACommit.class)){
//                LOG.debug("soaTransaction Filter execute no soaTransaction method:【{}】",method.getName());
//                return invoker.invoke(invocation);
//            }
            SOARollbackMeta rollbackMeta = SOATransactionBeanProcessor.METHOD_ROLLBACK_MAPPING.get(method);
            if(ObjectUtils.isEmpty(rollbackMeta)){
                throw new SOATransactionException(SOATransactionException.UNKNOWN_ROLLBACK_META_EXCEPTION,String.format("未找到soaTransaction方法:【%s】元数据",method));
            }
            int[] argsIndex = rollbackMeta.getArgs();
            String rollBackMethodName = rollbackMeta.getMethodName();
            String json = cacheWrapper.get(txId);

            Object[] params = new Object[rollbackMeta.getArgs().length];
            for (int i = 0; i < argsIndex.length; i++) {
                params[i] = arguments[argsIndex[i]];
            }
            SOATransactionContext context;
            if(!StringUtils.isEmpty(json)){
                 context =JSONObject.parseObject(json, SOATransactionContext.class);
            }else {
                 context = new SOATransactionContext();
            }
            context.addContextHolder(clz,rollBackMethodName,params);
            cacheWrapper.setex(txId,context,EXPIRE_TIME);
        }
        return invoker.invoke(invocation);
    }

}
