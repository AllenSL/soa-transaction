package com.asl.test.config;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * SOATransactionFilter
 * @author ansonglin
 */
@Activate(group = {CommonConstants.CONSUMER})
public class NSOATransactionFilter implements Filter {
    /**
     * 过期时间 60s*60*24=24h
     */
    public static int EXPIRE_TIME = 86400;



    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println();
        return invoker.invoke(invocation);
    }

}
