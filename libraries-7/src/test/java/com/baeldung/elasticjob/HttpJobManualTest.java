package com.baeldung.elasticjob;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.bootstrap.type.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.http.props.HttpJobProperties;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.junit.jupiter.api.Test;

public class HttpJobManualTest {

    @Test
    void whenSchedulingAHTTPJob_thenTheHTTPCallIsMade() throws Exception {
        JobConfiguration jobConfig = JobConfiguration.newBuilder("MyHttpJob", 3)
            .cron("0/5 * * * * ?")
            .jobParameter("Hello")
            .shardingItemParameters("0=a,1=b,2=c")
            .setProperty(HttpJobProperties.URI_KEY, "https://webhook.site/8b878497-d8ba-4a30-9f8b-223a30ee92c5")
            .setProperty(HttpJobProperties.METHOD_KEY, "POST")
            .setProperty(HttpJobProperties.DATA_KEY, "source=Baeldung")
            .build();

        new ScheduleJobBootstrap(createRegistryCenter(), "HTTP", jobConfig)
            .schedule();

        // Keep the test alive indefinitely so that the job can run
        Thread.currentThread().join();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2181",
            HttpJobManualTest.class.getName()));
        regCenter.init();
        return regCenter;
    }

}
