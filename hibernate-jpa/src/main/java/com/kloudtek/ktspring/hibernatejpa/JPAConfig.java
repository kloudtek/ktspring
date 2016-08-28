/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring.hibernatejpa;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by yannick on 25/8/16.
 */
public class JPAConfig {
    private DataSource dataSource;
    private Properties jpaProperties;
    private String[] packageToScan;
    private boolean jtaDatasource;

    public JPAConfig() {
    }

    public JPAConfig(Properties jpaProperties, DataSource dataSource, boolean jtaDatasource, String... packageToScan) {
        this.jpaProperties = jpaProperties;
        this.dataSource = dataSource;
        this.jtaDatasource = jtaDatasource;
        this.packageToScan = packageToScan;
    }

    public Properties getJpaProperties() {
        return jpaProperties;
    }

    public void setJpaProperties(Properties jpaProperties) {
        this.jpaProperties = jpaProperties;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public String[] getPackageToScan() {
        return packageToScan;
    }

    public void setPackageToScan(String[] packageToScan) {
        this.packageToScan = packageToScan;
    }

    public boolean isJtaDatasource() {
        return jtaDatasource;
    }

    public void setJtaDatasource(boolean jtaDatasource) {
        this.jtaDatasource = jtaDatasource;
    }
}
