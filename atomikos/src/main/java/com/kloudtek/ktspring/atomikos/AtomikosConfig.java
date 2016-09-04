/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring.atomikos;

import com.atomikos.icatch.config.UserTransactionServiceImp;
import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import java.util.Properties;

/**
 * Created by yannick on 3/9/16.
 */
@Configuration
public class AtomikosConfig {
    @Bean(initMethod = "init", destroyMethod = "shutdownForce")
    public UserTransactionServiceImp userTransactionService() {
        Properties p = new Properties();
        p.setProperty("com.atomikos.icatch.service", "com.atomikos.icatch.standalone.UserTransactionServiceFactory");
        return new UserTransactionServiceImp(p);
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    @DependsOn("userTransactionService")
    public UserTransactionManager UserTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(false);
        userTransactionManager.setStartupTransactionService(false);
        return userTransactionManager;
    }

    @Bean
    @DependsOn("userTransactionService")
    public UserTransactionImp userTransactionImp() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(3000);
        return userTransactionImp;
    }

    @Bean
    @DependsOn("userTransactionService")
    public JtaTransactionManager jtaTransactionManager() {
        return new JtaTransactionManager(UserTransactionManager(), UserTransactionManager());
    }
}
