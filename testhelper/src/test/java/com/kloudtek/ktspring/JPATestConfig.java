/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

//import com.kloudtek.ktspring.artemis.ArtemisXAEmbeddedServerConfig;
//import com.kloudtek.ktspring.hibernatejpa.HibernateJPAConfig;
//import com.kloudtek.ktspring.hibernatejpa.HibernateJPATransactionConfig;
//import com.kloudtek.ktspring.jms.JMSDurableTopicConfig;
//import com.kloudtek.ktspring.jms.JMSQueueConfig;
//import com.kloudtek.ktspring.jms.JMSTopicConfig;
//import com.kloudtek.ktspring.jms.JMSTopicTemplateConfig;
//import org.apache.activemq.artemis.integration.spring.SpringJmsBootstrap;
//import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
//import org.hibernate.dialect.HSQLDialect;
//import org.hsqldb.jdbc.JDBCDataSourceFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.jms.connection.CachingConnectionFactory;
//import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//import org.springframework.transaction.support.TransactionTemplate;
//
//import javax.jms.ConnectionFactory;
//import javax.sql.DataSource;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.util.Properties;
//
///**
// * Created by yannick on 23/8/16.
// */
//@SuppressWarnings("Duplicates")
//@Configuration
//@Import({HibernateJPAConfig.class, HibernateJPATransactionConfig.class, JMSQueueConfig.class, JMSTopicConfig.class, JMSDurableTopicConfig.class, JMSTopicTemplateConfig.class})
public class JPATestConfig {
//    @Bean
//    public TransactionTemplate txTemplate(PlatformTransactionManager transactionManager) {
//        TransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
//        return new TransactionTemplate(transactionManager, def);
//    }
//
//    @Bean
//    public com.kloudtek.ktspring.hibernatejpa.JPAConfig jpaConfig() throws Exception {
//        Properties p = new Properties();
//        p.setProperty("hibernate.dialect", HSQLDialect.class.getName());
//        p.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//        p.setProperty("hibernate.showsql", "true");
//        return new com.kloudtek.ktspring.hibernatejpa.JPAConfig(p, datasource(), false, "com.kloudtek.ktspring");
//    }
//
//    public DataSource datasource() throws Exception {
//        EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
////        try (Connection connection = embeddedDatabase.getConnection()) {
////            try (Statement st = connection.createStatement()) {
////                st.execute("SET DATABASE EVENT LOG SQL LEVEL 3");
////            }
////        }
//        return embeddedDatabase;
//    }
//
//    @Value("${jms.clientid?:test}")
//    private String jmsClientId;
//
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    public SpringJmsBootstrap artemisServer() {
//        return new SpringJmsBootstrap();
//    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("vm://0");
//        connectionFactory.setClientID(jmsClientId);
////        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory);
//        TransactionAwareConnectionFactoryProxy txConnectionFactory = new TransactionAwareConnectionFactoryProxy(connectionFactory);
//        return txConnectionFactory;
//    }
}
