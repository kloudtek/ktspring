/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring.hibernatejpa;

import org.hibernate.dialect.DerbyTenSevenDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by yannick on 3/9/16.
 */
@Configuration
@Import({HibernateJPATransactionConfig.class, HibernateJPAConfig.class})
public class Config {
    @Bean
    public JPAConfig jpaConfig() {
        Properties p = new Properties();
        p.setProperty("hibernate.dialect", DerbyTenSevenDialect.class.getName());
        p.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        p.setProperty("hibernate.showsql", "true");
        return new JPAConfig(p, datasource(), false, "com.kloudtek.ktspring");
    }

    public DataSource datasource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }
}
