/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kloudtek.ktspring;

import com.kloudtek.util.ThreadUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

/**
 * Created by yannick on 23/8/16.
 */
@SuppressWarnings("JpaQlInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class StandaloneEEConfigTest {
    private static final Logger logger = Logger.getLogger(StandaloneEEConfigTest.class.getName());
    public static final int MAX_DB_TX = 100;
    public static final String QUEUE = "queue";
    public static final String TOPIC = "testtopic";
    public static final String QUEUE2 = "testqueue2";
    @Autowired
    private TransactionTemplate tx;
    @Autowired
    @Qualifier("queue")
    private JmsTemplate jms;
    @Autowired
    @Qualifier("topic")
    private JmsTemplate topicJms;
    @PersistenceContext
    private EntityManager entityManager;
    private final HashSet<String> receivedQueue = new HashSet<>();
    private final HashSet<String> receivedTopic = new HashSet<>();
    private final HashSet<String> receivedQueue2 = new HashSet<>();

    @Before()
    public void init() {
        receivedQueue.clear();
        receivedTopic.clear();
        receivedQueue2.clear();
        tx.execute(status -> {
            entityManager.createQuery("delete from TestObj").executeUpdate();
            return null;
        });
    }

    @Test
    public void testStandalone() {
        System.out.println("Sending");
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            tx.execute(transactionStatus -> {
                tx.execute(status -> {
                    entityManager.persist(new TestObj(finalI));
                    jms.send(QUEUE, session -> session.createTextMessage(Integer.toString(finalI)));
                    return null;
                });
                topicJms.send(TOPIC, session -> session.createTextMessage(Integer.toString(finalI)));
                return null;
            });
        }
        System.out.println("Checking database");
        List list = entityManager.createQuery("select t from TestObj t").getResultList();
        assertEquals(MAX_DB_TX, list.size());
        System.out.println("Checking received queue");
        checkReceived(receivedQueue);
        System.out.println("Checking received topic");
        checkReceived(receivedTopic);
        System.out.println("Checking received queue 2");
        checkReceived(receivedQueue2);
    }

    @Test
    public void testExplicitRollback() {
        assertEquals(0, entityManager.createQuery("select t from TestObj t").getResultList().size());
        try {
            tx.execute(status -> {
                entityManager.persist(new TestObj(0));
                jms.send(QUEUE, session -> session.createTextMessage("test"));
                status.setRollbackOnly();
                return true;
            });
        } catch (Exception e) {
            System.out.println();
        }
        ThreadUtils.sleep(1000);
        assertEquals(0, entityManager.createQuery("select t from TestObj t").getResultList().size());
        assertEquals(0, receivedQueue.size());
    }

    @Test
    public void testExplicitRollbackByException() {
        assertEquals(0, entityManager.createQuery("select t from TestObj t").getResultList().size());
        try {
            tx.execute(status -> {
                entityManager.persist(new TestObj(0));
                jms.send(QUEUE, session -> session.createTextMessage("test"));
                throw new RuntimeException("fail");
            });
        } catch (RuntimeException e) {
            assertEquals("fail", e.getMessage());
        }
        ThreadUtils.sleep(1000);
        assertEquals(0, entityManager.createQuery("select t from TestObj t").getResultList().size());
        assertEquals(0, receivedQueue.size());
    }

    private void checkReceived(HashSet<String> received) {
        wait(25000, () -> {
            assertEquals(100, received.size());
            for (int i = 0; i < 100; i++) {
                Assert.assertTrue(received.contains(Integer.toString(i)));
            }
            return true;
        });
    }

    @JmsListener(destination = QUEUE)
    public void receivedQueueMsg(String txt) {
        synchronized (receivedQueue) {
            logger.info("Received queue : " + txt);
            receivedQueue.add(txt);
        }
    }

    @JmsListener(destination = QUEUE2)
    public void receivedQueue2Msg(String txt) {
        synchronized (receivedQueue2) {
            logger.info("Received queue2 : " + txt);
            receivedQueue2.add(txt);
        }
    }

    @JmsListener(destination = TOPIC, containerFactory = "durableTopicJmsListenerContainerFactory")
    public void receivedTopicMsg(String txt) {
        synchronized (receivedTopic) {
            logger.info("Received topic " + txt);
            receivedTopic.add(txt);
            jms.send(QUEUE2, session -> session.createTextMessage(txt));
        }
    }

    public static void wait(long timeout, Callable<Boolean> until) {
        if (until == null) {
            throw new IllegalArgumentException("until cannot be null");
        }
        long expiry = System.currentTimeMillis() + timeout;
        Throwable ex = null;
        for (; ; ) {
            try {
                if (Boolean.TRUE.equals(until.call())) {
                    return;
                }
            } catch (Throwable e) {
                ex = e;
            }
            if (System.currentTimeMillis() > expiry) {
                throw new AssertionError("Failed to perform until operation", ex);
            }
            ThreadUtils.sleep(500L);
        }
    }
}
