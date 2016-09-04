package com.kloudtek.ktspring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.Callable;

import static org.junit.Assert.*;

/**
 * Created by yannick on 4/9/16.
 */
@SuppressWarnings("JpaQlInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@EnableTransactionManagement(proxyTargetClass = true)
@ContextConfiguration(classes = Config.class)
public class AtomikosArtemisHibernateConfigTest {
    @Autowired
    private TestHelper testHelper;

    @Before
    public void reset() {
        testHelper.reset();
    }

    @Test
    public void testStandalone() {
        testHelper.testStandalone();
    }

    @Test
    public void testExplicitRollback() {
        testHelper.testExplicitRollback();
    }

    @Test
    public void testExplicitRollbackByException() {
        testHelper.testExplicitRollbackByException();
    }
}