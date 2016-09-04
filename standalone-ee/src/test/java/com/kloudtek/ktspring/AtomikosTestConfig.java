/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import com.kloudtek.ktspring.artemis.ArtemisXAEmbeddedServerConfig;
import com.kloudtek.ktspring.atomikos.AtomikosConfig;
import com.kloudtek.ktspring.atomikos.AtomikosHibernateConfig;
import com.kloudtek.ktspring.hibernatejpa.HibernateJPAConfig;
import com.kloudtek.ktspring.jms.JMSDurableTopicConfig;
import com.kloudtek.ktspring.jms.JMSQueueConfig;
import com.kloudtek.ktspring.jms.JMSTopicConfig;
import com.kloudtek.ktspring.jms.JMSTopicTemplateConfig;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by yannick on 23/8/16.
 */
@Configuration
@Import({XATestConfig.class, AtomikosConfig.class, AtomikosHibernateConfig.class, ArtemisXAEmbeddedServerConfig.class, HibernateJPAConfig.class, ArtemisXAEmbeddedServerConfig.class, JMSQueueConfig.class, JMSTopicConfig.class, JMSDurableTopicConfig.class, JMSTopicTemplateConfig.class})
public class AtomikosTestConfig {
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        AtomikosDataSourceBean datasource = new AtomikosDataSourceBean();
        datasource.setUniqueResourceName("xads");
        JDBCXADataSource hsqlDs = new JDBCXADataSource();
        hsqlDs.setURL("jdbc:hsqldb:mem:test?hsqldb.applog=3");
        hsqlDs.setUser("sa");
        hsqlDs.setPassword("");
//        EmbeddedXADataSource xaDataSource = new EmbeddedXADataSource();
//        xaDataSource.setCreateDatabase("create");
//        xaDataSource.setDatabaseName("memory:myDB");
//        xaDataSource.setUser("sa");
//        xaDataSource.setPassword("");
        datasource.setXaDataSource(hsqlDs);

//        try (Connection connection = datasource.getConnection()) {
//            try (Statement st = connection.createStatement()) {
//                st.execute("SET DATABASE EVENT LOG SQL LEVEL 3");
//            }
//        }
        return datasource;
    }
}
