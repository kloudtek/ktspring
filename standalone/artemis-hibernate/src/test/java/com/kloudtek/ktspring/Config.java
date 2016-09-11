/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import com.kloudtek.ktspring.artemis.ArtemisXAEmbeddedServerConfig;
import com.kloudtek.ktspring.hibernatejpa.HibernateJPAConfig;
import com.kloudtek.ktspring.hibernatejpa.HibernateJPATransactionConfig;
import com.kloudtek.ktspring.hibernatejpa.JPAConfig;
import com.kloudtek.ktspring.hibernatejpa.JPAParams;
import com.kloudtek.ktspring.jms.JMSDurableTopicConfig;
import com.kloudtek.ktspring.jms.JMSQueueConfig;
import com.kloudtek.ktspring.jms.JMSTopicConfig;
import com.kloudtek.ktspring.jms.JMSTopicTemplateConfig;
import org.apache.activemq.artemis.integration.spring.SpringJmsBootstrap;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.hibernate.Hibernate;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.jms.ConnectionFactory;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.List;
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
