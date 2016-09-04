/*
 * Copyright (c) 2016 Kloudtek Ltd
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
public class HibernateJPAConfig {
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