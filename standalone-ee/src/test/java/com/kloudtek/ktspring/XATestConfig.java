/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import com.kloudtek.ktspring.hibernatejpa.JPAConfig;
import org.hibernate.dialect.HSQLDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by yannick on 23/8/16.
 */
@SuppressWarnings("Duplicates")
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class XATestConfig {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionTemplate txTemplate() {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public JPAConfig jpaConfig(DataSource datasource) throws Exception {
        Properties p = new Properties();
        p.setProperty("hibernate.dialect", HSQLDialect.class.getName());
        p.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        p.setProperty("hibernate.showsql", "true");
        return new JPAConfig(p, datasource, true, "com.kloudtek.ktspring");
    }
}
