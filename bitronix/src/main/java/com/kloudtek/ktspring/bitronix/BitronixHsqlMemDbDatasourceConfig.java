package com.kloudtek.ktspring.bitronix;

import bitronix.tm.resource.jdbc.PoolingDataSource;
import com.kloudtek.ktspring.hibernatejpa.JPAParams;
import org.hibernate.dialect.HSQLDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created by yannick on 4/9/16.
 */
@Configuration
public class BitronixHsqlMemDbDatasourceConfig {
    @Bean
    public DataSource datasource() throws Exception {
        PoolingDataSource dataSource = new PoolingDataSource();
        dataSource.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        dataSource.setUniqueName("hsqldb");
        dataSource.setMaxPoolSize(5);
        dataSource.setAllowLocalTransactions(true);
        dataSource.getDriverProperties().setProperty("driverClassName", "org.hsqldb.jdbcDriver");
        dataSource.getDriverProperties().setProperty("url", "jdbc:hsqldb:mem:test");
        dataSource.getDriverProperties().setProperty("user", "sa");
        dataSource.getDriverProperties().setProperty("password", "theSaPassword");
        return dataSource;
    }

    @Bean
    public JPAParams hsqlJpaParams() {
        return new JPAParams("hibernate.dialect", HSQLDialect.class.getName());
    }
}
