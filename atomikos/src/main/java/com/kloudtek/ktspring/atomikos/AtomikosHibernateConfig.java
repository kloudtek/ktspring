/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring.atomikos;

import com.kloudtek.ktspring.hibernatejpa.JPAParams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * Created by yannick on 26/8/16.
 */
@Configuration
public class AtomikosHibernateConfig {
    @Bean
    public JPAParams jpaParams() {
        Properties p = new Properties();
        p.setProperty("hibernate.connection.handling_mode", "DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION");
        p.setProperty("hibernate.current_session_context_class", "jta");
        p.setProperty("hibernate.transaction.jta.platform", AtomikosPlatform.class.getName());
        return new JPAParams(p);
    }
}
