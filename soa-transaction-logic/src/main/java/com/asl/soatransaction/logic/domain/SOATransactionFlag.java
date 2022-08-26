package com.asl.soatransaction.logic.domain;

import com.asl.soatransaction.logic.SOATransactionStatus;

import java.io.Serializable;
import java.util.UUID;

/**
 * 事务标记
 */
public class SOATransactionFlag implements Serializable {

    private String txId = UUID.randomUUID().toString();

    private SOATransactionStatus status = SOATransactionStatus.START;

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public SOATransactionStatus getStatus() {
        return status;
    }

    public void setStatus(SOATransactionStatus status) {
        this.status = status;
    }
}
