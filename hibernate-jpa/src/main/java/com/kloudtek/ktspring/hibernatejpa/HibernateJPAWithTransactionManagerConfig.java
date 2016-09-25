/*
 * Copyright (c) 2016 Kloudtek Ltd
 */

package com.kloudtek.ktspring.hibernatejpa;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Properties;

import static com.kloudtek.ktspring.hibernatejpa.HibernateJPAConfig.createEntityManagerFactory;

/**
 * Created by yannick on 23/8/16.
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class HibernateJPAWithTransactionManagerConfig {
    @Autowired(required = false)
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private List<JPAParams> jpaParamsList;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(JPAConfig config) {
        return createEntityManagerFactory(config, jpaParamsList);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
