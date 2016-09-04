/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import com.kloudtek.ktspring.artemis.ArtemisXAEmbeddedServerConfig;
import com.kloudtek.ktspring.bitronix.BitronixConfig;
import com.kloudtek.ktspring.bitronix.BitronixHibernateConfig;
import com.kloudtek.ktspring.hibernatejpa.HibernateJPAConfig;
import com.kloudtek.ktspring.jms.JMSDurableTopicConfig;
import com.kloudtek.ktspring.jms.JMSQueueConfig;
import com.kloudtek.ktspring.jms.JMSTopicConfig;
import com.kloudtek.ktspring.jms.JMSTopicTemplateConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by yannick on 23/8/16.
 */
@SuppressWarnings("Duplicates")
@Configuration
@Import({XATestConfig.class, BitronixConfig.class, BitronixHibernateConfig.class, HibernateJPAConfig.class, ArtemisXAEmbeddedServerConfig.class, JMSQueueConfig.class, JMSTopicConfig.class, JMSDurableTopicConfig.class, JMSTopicTemplateConfig.class})
public class BitronixTestConfig {
    @Bean
    public DataSource datasource() throws Exception {
        PoolingDataSource dataSource = new PoolingDataSource();
        dataSource.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        dataSource.setUniqueName("hsqldb");
        dataSource.setMaxPoolSize(5);
        dataSource.setAllowLocalTransactions(true);
        dataSource.getDriverProperties().setProperty("driverClassName", "org.hsqldb.jdbcDriver");
        dataSource.getDriverProperties().setProperty("url", "jdbc:hsqldb:mem:test?hsqldb.applog=3");
        dataSource.getDriverProperties().setProperty("user", "sa");
        dataSource.getDriverProperties().setProperty("password", "theSaPassword");
        try (Connection connection = dataSource.getConnection()) {
            try (Statement st = connection.createStatement()) {
                st.execute("SET DATABASE EVENT LOG SQL LEVEL 3");
            }
        }
        return dataSource;
    }
}
