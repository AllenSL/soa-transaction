package com.asl.soatransaction.logic;

/**
 * 事务回滚元数据类
 * @author ansonglin
 */
public class SOARollbackMeta {

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
    private int[] args;


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

    public int[] getArgs() {
        return args;
    }

    public void setArgs(int[] args) {
        this.args = args;
    }
}
