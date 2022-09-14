package com.asl.soatransaction.logic.actuator;

import com.alibaba.fastjson.JSONObject;
import com.asl.soatransaction.logic.exp.SOATransactionException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class SimpleInvoker extends AbstractInvoker{

    Logger LOG = LoggerFactory.getLogger(SimpleInvoker.class);

    @Override
    public void processRequest(Object object, String methodName, Object[] args) {
        LOG.info("当前执行类:【{}】,方法:【{}】",object.getClass(),methodName);
        Class<?>[] parameterTypes = ClassUtils.toClass(args);
        parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
        Method accessibleMethod = MethodUtils.getAccessibleMethod(object.getClass(), methodName, parameterTypes);
        if(ObjectUtils.isEmpty(accessibleMethod)){
            throw new SOATransactionException(SOATransactionException.METHOD_PARAMETERS_NOT_MATCH_EXCEPTION,
                    String.format("类:【%s】 方法:【%s】和入参:【%s】不匹配",object.getClass().getName(),methodName, JSONObject.toJSONString(args)));
        }
    }

    @Override
    public void processResponse(Object o) {
        LOG.info("response:【{}】",JSONObject.toJSONString(o));
    }

}
