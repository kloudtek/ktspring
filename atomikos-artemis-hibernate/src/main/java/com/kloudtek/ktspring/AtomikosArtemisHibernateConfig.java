/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring;

import com.kloudtek.ktspring.atomikos.AtomikosConfig;
import com.kloudtek.ktspring.hibernatejpa.HibernateJPAConfig;
import com.kloudtek.ktspring.jms.JMSDurableTopicConfig;
import com.kloudtek.ktspring.jms.JMSQueueConfig;
import com.kloudtek.ktspring.jms.JMSTopicConfig;
import com.kloudtek.ktspring.jms.JMSTopicTemplateConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yannick on 23/8/16.
 */
@Configuration
@Import({HibernateJPAConfig.class, AtomikosConfig.class, JMSQueueConfig.class, JMSTopicConfig.class, JMSDurableTopicConfig.class, JMSTopicTemplateConfig.class})
public class AtomikosArtemisHibernateConfig {
}
