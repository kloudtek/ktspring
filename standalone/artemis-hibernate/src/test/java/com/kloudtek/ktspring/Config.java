/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import com.kloudtek.ktspring.hibernatejpa.JPAConfig;
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
 * Created by yannick on 23/8/16.
 */
@SuppressWarnings("Duplicates")
@Configuration
@Import({StandaloneArtemisHibernateConfig.class})
public class Config {
    @Bean
    public TestHelper testHelper() {
        return new TestHelper();
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
    }

    @Bean
    public TransactionTemplate txTemplate(PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public JPAConfig jpaConfig(DataSource datasource) throws Exception {
        Properties p = new Properties();
        p.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        p.setProperty("hibernate.showsql", "true");
        return new JPAConfig(p, datasource, false, "com.kloudtek.ktspring");
    }
}
