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

/**
 * Created by yannick on 23/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class})
public class StandaloneEEConfigTest {
    public static final int MAX_DB_TX = 100;
    public static final String QUEUE = "queue";
    public static final String TOPIC = "testtopic";
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

    @Test
    public void testStandalone() {
        System.out.println("Sending");
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            tx.execute(transactionStatus -> {
                entityManager.persist(new TestObj(finalI));
                jms.send(QUEUE, session -> session.createTextMessage(Integer.toString(finalI)));
                topicJms.send(TOPIC, session -> session.createTextMessage(Integer.toString(finalI)));
                return null;
            });
        }
        System.out.println("Checking database");
        List list = entityManager.createQuery("select t from TestObj t").getResultList();
        Assert.assertEquals(MAX_DB_TX, list.size());
        System.out.println("Checking received queue");
        checkReceived(receivedQueue);
        System.out.println("Checking received topic");
        checkReceived(receivedTopic);
    }

    private void checkReceived(HashSet<String> received) {
        wait(25000, () -> {
            Assert.assertEquals(100, received.size());
            for (int i = 0; i < 100; i++) {
                Assert.assertTrue(received.contains(Integer.toString(i)));
            }
            return true;
        });
    }

    @JmsListener(destination = QUEUE)
    public void receivedQueueMsg(String txt) {
        synchronized (receivedQueue) {
            System.out.println("Received queue " + txt);
            receivedQueue.add(txt);
        }
    }

    @JmsListener(destination = TOPIC, containerFactory = "durableTopicJmsListenerContainerFactory")
    public void receivedTopicMsg(String txt) {
        synchronized (receivedTopic) {
            System.out.println("Received topic " + txt);
            receivedTopic.add(txt);
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
