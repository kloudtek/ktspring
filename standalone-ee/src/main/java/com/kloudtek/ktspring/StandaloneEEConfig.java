/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring;

import com.kloudtek.ktspring.bitronix.BitronixConfig;
import com.kloudtek.ktspring.bitronix.BitronixHibernateConfig;
import com.kloudtek.ktspring.hibernatejpa.HibernateJPAJTAConfig;
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
@EnableTransactionManagement(proxyTargetClass = true)
@Import({HibernateJPAJTAConfig.class, BitronixConfig.class, BitronixHibernateConfig.class, JMSQueueConfig.class, JMSTopicConfig.class, JMSDurableTopicConfig.class, JMSTopicTemplateConfig.class})
public class StandaloneEEConfig {
}
