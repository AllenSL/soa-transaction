package com.asl.soatransaction.logic.actuator;

import com.asl.soatransaction.logic.exp.SOATransactionException;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractInvoker implements Invoker{

    Logger LOG = LoggerFactory.getLogger(AbstractInvoker.class);

    private String eventId;

    public String getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public void invoke(Object target, String methodName, Object[] args,String eventId) {
        if(!StringUtils.isEmpty(eventId)){
            setEventId(eventId);
        }
        this.processRequest(target,methodName,args);
        Object o = null;
        try {
             o = MethodUtils.invokeMethod(target, methodName, args);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            LOG.error("方法:【{}】,eventId:【{}】执行失败,原因:【{}】",methodName,getEventId(),e.getMessage(),e);
            throw new SOATransactionException(SOATransactionException.ROLLBACK_EXCEPTION,String.format("方法:【%s】,执行rpc方法:【%s】事务ID:【%s】事务回滚失败",methodName,methodName, getEventId()));
        }
        this.processResponse(o);
    }
}
