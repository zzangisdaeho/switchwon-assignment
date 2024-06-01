package com.switchwon.paymentimpl.util;

import org.springframework.transaction.support.TransactionSynchronizationManager;

public class TransactionUtils {

    public static String getCurrentTransactionId() {

        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            return TransactionSynchronizationManager.getCurrentTransactionName();
        } else {
            return "No active transaction";
        }
    }
}
