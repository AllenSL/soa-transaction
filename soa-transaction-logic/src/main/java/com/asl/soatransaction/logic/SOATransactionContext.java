package com.asl.soatransaction.logic;

import java.util.ArrayList;
import java.util.List;

/**
 * 事务回滚方法上下文
 * @author ansonglin
 */
public class SOATransactionContext {

    /**
     * 回调方法回滚链
     */
    public List<SOATransactionContext.PerServiceContext> contextHolders = new ArrayList<SOATransactionContext.PerServiceContext>();

    public List<PerServiceContext> getContextHolders() {
        return contextHolders;
    }

    public void setContextHolders(List<PerServiceContext> contextHolders) {
        this.contextHolders = contextHolders;
    }

    public void addContextHolder(Class<?> clz,String methodName,Object[] args,Class<?>[] parameterTypes){
        contextHolders.add(new PerServiceContext(clz,methodName,args,parameterTypes));
    }

    public static class PerServiceContext{
        /**
         * 回滚方法所属类
         */
        private Class<?> clz;

        /**
         * 回滚方法
         */
        private String methodName;

        /**
         * 回滚参数下标(请求方法的入参)
         */
        private Object[] args;

        /**
         *参数类型
         */
        private Class<?>[] parameterTypes;

        public PerServiceContext(Class<?> clz, String methodName, Object[] args,Class<?>[] parameterTypes) {
            this.clz = clz;
            this.methodName = methodName;
            this.args = args;
            this.parameterTypes = parameterTypes;
        }

        public Class<?> getClz() {
            return clz;
        }

        public void setClz(Class<?> clz) {
            this.clz = clz;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        public Class<?>[] getParameterTypes() {
            return parameterTypes;
        }

        public void setParameterTypes(Class<?>[] parameterTypes) {
            this.parameterTypes = parameterTypes;
        }
    }

}
