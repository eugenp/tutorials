package com.baeldung.apache.curator.connection;

import static com.jayway.awaitility.Awaitility.await;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.junit.Test;

public class ConnectionManagementManualTest {

    @Test
    public void givenRunningZookeeper_whenOpenConnection_thenClientIsOpened()
        throws Exception {
        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        RetryPolicy retryPolicy = new RetryNTimes(maxRetries,
            sleepMsBetweenRetries);

        try (CuratorFramework client = CuratorFrameworkFactory
            .newClient("127.0.0.1:2181", retryPolicy)) {
            client.start();

            assertThat(client.checkExists()
                .forPath("/")).isNotNull();
        }
    }

    @Test
    public void givenRunningZookeeper_whenOpenConnectionUsingAsyncNotBlocking_thenClientIsOpened()
        throws InterruptedException {
        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        RetryPolicy retryPolicy = new RetryNTimes(maxRetries,
            sleepMsBetweenRetries);

        try (CuratorFramework client = CuratorFrameworkFactory
            .newClient("127.0.0.1:2181", retryPolicy)) {
            client.start();
            AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);

            AtomicBoolean exists = new AtomicBoolean(false);

            async.checkExists()
                .forPath("/")
                .thenAcceptAsync(s -> exists.set(s != null));

            await().until(() -> assertThat(exists.get()).isTrue());
        }
    }

    @Test
    public void givenRunningZookeeper_whenOpenConnectionUsingAsyncBlocking_thenClientIsOpened()
        throws InterruptedException {
        int sleepMsBetweenRetries = 100;
        int maxRetries = 3;
        RetryPolicy retryPolicy = new RetryNTimes(maxRetries,
            sleepMsBetweenRetries);

        try (CuratorFramework client = CuratorFrameworkFactory
            .newClient("127.0.0.1:2181", retryPolicy)) {
            client.start();
            AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);

            AtomicBoolean exists = new AtomicBoolean(false);

            async.checkExists()
                .forPath("/")
                .thenAccept(s -> exists.set(s != null));

            await().until(() -> assertThat(exists.get()).isTrue());
        }
    }
}
