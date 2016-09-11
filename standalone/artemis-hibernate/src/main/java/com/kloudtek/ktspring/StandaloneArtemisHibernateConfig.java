package com.kloudtek.ktspring;

import com.kloudtek.ktspring.hibernatejpa.HibernateJPAConfig;
import com.kloudtek.ktspring.hibernatejpa.JPAConfig;
import com.kloudtek.ktspring.hibernatejpa.JPAParams;
import org.apache.activemq.artemis.integration.spring.SpringJmsBootstrap;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.jms.ConnectionFactory;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by yannick on 4/9/16.
 */
@Configuration
public class StandaloneArtemisHibernateConfig {
    @Value("${jms.clientid?:test}")
    private String jmsClientId;
    @Autowired(required = false)
    private List<JPAParams> jpaParamsList;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SpringJmsBootstrap artemisServer() {
        return new SpringJmsBootstrap();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("vm://0");
        connectionFactory.setClientID(jmsClientId);
        TransactionAwareConnectionFactoryProxy transactionAwareConnectionFactoryProxy = new TransactionAwareConnectionFactoryProxy(connectionFactory);
        transactionAwareConnectionFactoryProxy.setSynchedLocalTransactionAllowed(true);
        return transactionAwareConnectionFactoryProxy;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JPAConfig config) {
        return HibernateJPAConfig.createEntityManagerFactory(config, jpaParamsList);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}