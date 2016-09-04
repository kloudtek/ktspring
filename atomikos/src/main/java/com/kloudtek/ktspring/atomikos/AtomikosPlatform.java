/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring.atomikos;

import com.atomikos.icatch.jta.J2eeTransactionManager;
import com.atomikos.icatch.jta.J2eeUserTransaction;
import org.hibernate.engine.transaction.jta.platform.internal.AbstractJtaPlatform;

import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

/**
 * Created by yannick on 3/9/16.
 */
public class AtomikosPlatform extends AbstractJtaPlatform {
    private static final long serialVersionUID = -1L;

    @Override
    protected TransactionManager locateTransactionManager() {
        return new J2eeTransactionManager();
    }

    @Override
    protected UserTransaction locateUserTransaction() {
        return new J2eeUserTransaction();
    }
}