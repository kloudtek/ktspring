package com.kloudtek.ktspring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yannick on 4/9/16.
 */
@SuppressWarnings("JpaQlInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@EnableTransactionManagement(proxyTargetClass = true)
@ContextConfiguration(classes = Config.class)
public class StandaloneArtemisHibernateConfigTest {
    @Autowired
    private TestHelper testHelper;

    @Before
    public void reset() {
        testHelper.reset();
    }

    @Test
    public void testDb() {
        testHelper.testDb();
    }

    @Test
    public void testCombined() {
        testHelper.testCombined();
    }

    @Test
    public void testExplicitRollback() {
        testHelper.testExplicitRollback();
    }

    @Test
    public void testOnlySentAfterCommit() {
        testHelper.testOnlySentAfterCommit();
    }

    @Test
    public void testExplicitRollbackByException() {
        testHelper.testExplicitRollbackByException();
    }
}