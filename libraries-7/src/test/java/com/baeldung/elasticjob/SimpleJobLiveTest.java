package com.baeldung.elasticjob;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.bootstrap.type.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.apache.shardingsphere.elasticjob.spi.executor.item.param.ShardingContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleJobLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleJobLiveTest.class);

    @Test
    void whenSchedulingASimpleJob_thenTheJobRuns() throws Exception {
        JobConfiguration jobConfig = JobConfiguration.newBuilder("MySimpleJob", 3)
            .cron("0/5 * * * * ?")
            .jobParameter("Hello")
            .shardingItemParameters("1=a,2=b,3=c")
            .build();

        new ScheduleJobBootstrap(createRegistryCenter(), new MySimpleJob(), jobConfig)
            .schedule();

        // Keep the test alive indefinitely so that the job can run
        Thread.currentThread().join();
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2181",
            SimpleJobLiveTest.class.getName()));
        regCenter.init();
        return regCenter;
    }

    public static class MySimpleJob implements SimpleJob {

        @Override
        public void execute(ShardingContext context) {
            LOG.info("Executing job. {}", context);
        }
    }
}
