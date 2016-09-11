/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring.jms;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

import static org.springframework.jms.listener.DefaultMessageListenerContainer.CACHE_CONSUMER;

/**
 * Created by yannick on 28/8/16.
 */
@Configuration
@EnableJms
public class JMSTopicConfig {
    @Bean
    public DefaultJmsListenerContainerFactory topicJmsListenerContainerFactory(ConnectionFactory connectionFactory, PlatformTransactionManager transactionManager) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setTransactionManager(transactionManager);
        factory.setConnectionFactory(connectionFactory);
        factory.setAutoStartup(true);
        factory.setPubSubDomain(true);
        factory.setSessionTransacted(true);
        factory.setCacheLevel(CACHE_CONSUMER);
        return factory;
    }

    @Bean
    @Qualifier("topic")
    public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}
