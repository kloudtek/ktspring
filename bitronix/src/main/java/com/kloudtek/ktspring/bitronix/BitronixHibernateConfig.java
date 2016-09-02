/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring.bitronix;

import com.kloudtek.ktspring.hibernatejpa.JPAParams;
import org.hibernate.engine.transaction.jta.platform.internal.BitronixJtaPlatform;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by yannick on 26/8/16.
 */
@Configuration
public class BitronixHibernateConfig {
    @Bean
    public JPAParams jpaParams() {
        Properties p = new Properties();
//        p.setProperty("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
        p.setProperty("hibernate.current_session_context_class", "jta");
        p.setProperty("hibernate.transaction.jta.platform", BitronixJtaPlatform.class.getName());
        return new JPAParams(p);
    }
}
