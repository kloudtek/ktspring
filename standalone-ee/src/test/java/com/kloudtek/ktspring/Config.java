/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import bitronix.tm.resource.jdbc.lrc.LrcXADataSource;
import com.kloudtek.ktspring.artemis.ArtemisXAEmbeddedServerConfig;
import com.kloudtek.ktspring.hibernatejpa.JPAConfig;
import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbcDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Properties;

/**
 * Created by yannick on 23/8/16.
 */
@Configuration
@Import({StandaloneEEConfig.class, ArtemisXAEmbeddedServerConfig.class})
public class Config {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionTemplate txTemplate() {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public JPAConfig jpaConfig() {
        Properties p = new Properties();
        p.setProperty("hibernate.dialect", HSQLDialect.class.getName());
        p.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        p.setProperty("hibernate.showsql", "true");
        return new JPAConfig(p, datasource(), true, "com.kloudtek.ktspring");
    }

    @Bean(initMethod = "init", destroyMethod = "close")
    public PoolingDataSource datasource() {
        PoolingDataSource dataSource = new PoolingDataSource();
        dataSource.setUniqueName("hsqldb");
        dataSource.setMaxPoolSize(10);
        dataSource.setAllowLocalTransactions(true);
        dataSource.setClassName(LrcXADataSource.class.getName());
        Properties p = new Properties();
        p.setProperty("url", "jdbc:hsqldb:mem:aname");
        p.setProperty("user", "sa");
        p.setProperty("driverClassName", jdbcDriver.class.getName());
        dataSource.setDriverProperties(p);
        return dataSource;
    }
}
