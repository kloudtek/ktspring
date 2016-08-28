/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring.hibernatejpa;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.List;
import java.util.Properties;

/**
 * Created by yannick on 23/8/16.
 */
@Configuration
public class HibernateJPAJTAConfig {
    @Autowired
    private JPAConfig config;
    @Autowired(required = false)
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<JPAParams> jpaParamsList;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        if (config.isJtaDatasource()) {
            entityManager.setJtaDataSource(config.getDataSource());
        } else {
            entityManager.setDataSource(config.getDataSource());
        }
        Properties p = new Properties();
        p.putAll(config.getJpaProperties());
        if (jpaParamsList != null) {
            for (JPAParams jpaParams : jpaParamsList) {
                p.putAll(jpaParams.getProperties());
            }
        }
        entityManager.setJpaProperties(p);
        entityManager.setPackagesToScan(config.getPackageToScan());
        entityManager.setPersistenceProvider(new HibernatePersistenceProvider());
        return entityManager;
    }
}
