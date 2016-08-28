/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring.artemis;

import com.kloudtek.ktspring.CoreConfig;
import org.apache.activemq.artemis.integration.spring.SpringJmsBootstrap;
import org.apache.activemq.artemis.jms.client.ActiveMQXAConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

/**
 * Created by yannick on 26/8/16.
 */
@Configuration
@Import(CoreConfig.class)
public class ArtemisXAEmbeddedServerConfig {
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Value("${jms.clientid?:test}")
    private String jmsClientId;

    @Bean(initMethod = "start", destroyMethod = "stop")
    public SpringJmsBootstrap artemisServer() {
        return new SpringJmsBootstrap();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        ActiveMQXAConnectionFactory connectionFactory = new ActiveMQXAConnectionFactory("vm://0");
        connectionFactory.setClientID(jmsClientId);
        return new CachingConnectionFactory(connectionFactory);
    }
}
