package com.baeldung.elasticjob;

import java.util.List;
import java.util.stream.Stream;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.bootstrap.type.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.dataflow.job.DataflowJob;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.spi.executor.item.param.ShardingContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataflowJobManualTest {
    private static final Logger LOG = LoggerFactory.getLogger(DataflowJobManualTest.class);

    @Test
    void whenSchedulingADataflowJob_thenTheJobRuns() throws Exception {
        JobConfiguration jobConfig = JobConfiguration.newBuilder("MyDataflowJob", 3)
            .cron("0/5 * * * * ?")
            .jobParameter("Hello")
            .shardingItemParameters("1=a,2=b,3=c")
            .setProperty("DataflowJobProperties.STREAM_PROCESS_KEY", "true")
            .build();

        new ScheduleJobBootstrap(createRegistryCenter(), new MyDataflowJob(), jobConfig)
            .schedule();

        // Keep the test alive indefinitely so that the job can run
        Thread.currentThread().join();
    }
    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2181",
            DataflowJobManualTest.class.getName()));
        regCenter.init();
        return regCenter;
    }

    public static class MyDataflowJob implements DataflowJob<String> {
        @Override
        public List<String> fetchData(ShardingContext shardingContext) {
            return Stream.of("a", "b", "c")
                .map(value -> value + "," + shardingContext.getJobParameter() + "," + shardingContext.getShardingParameter())
                .toList();
        }

        @Override
        public void processData(ShardingContext shardingContext, List<String> list) {
            LOG.info("Processing data {} for job {}", list, shardingContext);
        }
    }
}
