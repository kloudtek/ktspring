package com.kloudtek.ktspring;

import com.kloudtek.util.ThreadUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yannick on 4/9/16.
 */
public class TestHelper {
    static {
        System.setProperty("hsqldb.reconfig_logging", "false");
    }
    private static final Logger logger = Logger.getLogger(TestHelper.class.getName());
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

    public void reset() {
        receivedQueue.clear();
        receivedTopic.clear();
        receivedQueue2.clear();
        tx.execute(status -> {
            entityManager.createQuery("delete from TestObj").executeUpdate();
            entityManager.createQuery("delete from TestObj2").executeUpdate();
            return null;
        });
    }

    public void testDb() {
        System.out.println("START DB TEST");
        tx.execute(status -> {
            TestObj2 o2 = new TestObj2(0);
            entityManager.persist(o2);
            TestObj o1 = new TestObj(0, o2);
            entityManager.persist(o1);
            TestObj2 ro2x = entityManager.find(TestObj2.class, 0);
            entityManager.persist(ro2x);
            TestObj o1x = new TestObj(1, ro2x);
            entityManager.persist(o1x);
            // flush required with relationship..... no freaking idea why ?!?!
            entityManager.flush();
            return null;
        });
        tx.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                TestObj2 ro2x = entityManager.find(TestObj2.class, 0);
                Assert.assertNotNull(ro2x);
                entityManager.persist(ro2x);
                TestObj o1x = new TestObj(2, ro2x);
                entityManager.persist(o1x);
                entityManager.flush();
            }
        });
    }

    public void testCombined() {
        System.out.println("start standalone test");
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            tx.execute(transactionStatus -> {
                tx.execute(status -> {
                    TestObj2 o2 = new TestObj2(finalI);
                    entityManager.persist(o2);
                    entityManager.persist(new TestObj(finalI, o2));
                    jms.send(QUEUE, session -> session.createTextMessage(Integer.toString(finalI)));
                    entityManager.flush();
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

    public void testExplicitRollback() {
        assertEquals(0, entityManager.createQuery("select t from TestObj t").getResultList().size());
        try {
            tx.execute(status -> {
                TestObj2 o2 = new TestObj2();
                entityManager.persist(o2);
                entityManager.persist(new TestObj(0, o2));
                jms.send(QUEUE, session -> session.createTextMessage("test"));
                status.setRollbackOnly();
                return true;
            });
        } catch (Exception e) {
            System.out.println();
        }
        assertEquals(0, entityManager.createQuery("select t from TestObj t").getResultList().size());
        ThreadUtils.sleep(3000);
        assertEquals(0, receivedQueue.size());
    }

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
        assertEquals(0, entityManager.createQuery("select t from TestObj t").getResultList().size());
        ThreadUtils.sleep(3000);
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
            TestObj testObj = entityManager.find(TestObj.class, Integer.parseInt(txt));
            assertNotNull(testObj);
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
