package com.asl.soatransaction.logic.actuator;

/**
 * @author ansonglin
 */
public interface Invoker {

    void processRequest(Object object, String methodName, Object[] args);


    void invoke(Object object, String methodName, Object[] args,String eventId);


    void processResponse(Object o);

    /**
     * 事件id
     */
    void setEventId(String eventId);
}
