/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import com.kloudtek.ktspring.artemis.ArtemisXAEmbeddedServerConfig;
import com.kloudtek.ktspring.bitronix.BitronixConfig;
import com.kloudtek.ktspring.bitronix.BitronixHibernateConfig;
import com.kloudtek.ktspring.hibernatejpa.JPAConfig;
import org.hibernate.dialect.HSQLDialect;
import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Properties;

/**
 * Created by yannick on 23/8/16.
 */
@SuppressWarnings("Duplicates")
@Configuration
//@Import({StandaloneEEConfig.class, AtomikosConfig.class, AtomikosHibernateConfig.class, ArtemisXAEmbeddedServerConfig.class})
@Import({StandaloneEEConfig.class, BitronixConfig.class, BitronixHibernateConfig.class, ArtemisXAEmbeddedServerConfig.class})
public class Config {
    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionTemplate txTemplate() {
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public JPAConfig jpaConfig() throws Exception {
        Properties p = new Properties();
        p.setProperty("hibernate.dialect", HSQLDialect.class.getName());
        p.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        p.setProperty("hibernate.showsql", "true");
        return new JPAConfig(p, datasource(), true, "com.kloudtek.ktspring");
    }

    public DataSource datasource() throws Exception {
        Properties p = new Properties();
        p.setProperty("url", "jdbc:hsqldb:mem:test?hsqldb.applog=3");
        p.setProperty("username", "sa");
        p.setProperty("password", "");
        DataSource dataSource = JDBCDataSourceFactory.createDataSource(p);
        try (Connection connection = dataSource.getConnection()) {
            try (Statement st = connection.createStatement()) {
                st.execute("SET DATABASE EVENT LOG SQL LEVEL 3");
            }
        }
        return dataSource;
    }
}
