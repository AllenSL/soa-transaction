package com.asl.soatransaction.logic.exp;

/**
 * SOATransactionException
 * @author ansonglin
 */
public class SOATransactionException extends RuntimeException{

    /**
     * 接口解析异常
     */
    public static final int INTERFACE_PARSE_EXCEPTION = 0;
    /**
     * 检查异常
     */
    public static final int CHECK_EXCEPTION = 1;
    /**
     * 方法回滚异常
     */
    public static final int ROLLBACK_EXCEPTION = 2;

    /**
     * 未找到回滚元数据异常
     */
    public static final int UNKNOWN_ROLLBACK_META_EXCEPTION = 3;

    /**
     * RpcException cannot be extended, use error code for exception type to keep compatibility
     */
    private int code;

    public SOATransactionException(){
        super();
    }

    public SOATransactionException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public SOATransactionException(int code, String message) {
        super(message);
        this.code = code;
    }

    public SOATransactionException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }
}
