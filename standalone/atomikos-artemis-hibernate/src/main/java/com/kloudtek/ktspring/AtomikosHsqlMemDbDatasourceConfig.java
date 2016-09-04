package com.kloudtek.ktspring;

import com.atomikos.jdbc.AtomikosDataSourceBean;
import org.hsqldb.jdbc.pool.JDBCXADataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by yannick on 4/9/16.
 */
@Configuration
public class AtomikosHsqlMemDbDatasourceConfig {
    @Bean(initMethod = "init", destroyMethod = "close")
    public DataSource dataSource() throws SQLException {
        AtomikosDataSourceBean datasource = new AtomikosDataSourceBean();
        datasource.setUniqueResourceName("xads");
        JDBCXADataSource hsqlDs = new JDBCXADataSource();
        hsqlDs.setURL("jdbc:hsqldb:mem:test?hsqldb.applog=3");
        hsqlDs.setUser("sa");
        hsqlDs.setPassword("");
        datasource.setXaDataSource(hsqlDs);
        return datasource;
    }
}
