package com.asl.soatransaction.logic.exp;

/**
 * SOATransactionException
 * @author ansonglin
 */
public class SOATransactionException extends RuntimeException{

    public static final int CHECK_EXCEPTION = 1;

    public static final int INTERFACE_PARSE_EXCEPTION = 0;

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
