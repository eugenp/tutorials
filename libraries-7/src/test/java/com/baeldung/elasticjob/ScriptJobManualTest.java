package com.baeldung.elasticjob;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;

import org.apache.shardingsphere.elasticjob.api.JobConfiguration;
import org.apache.shardingsphere.elasticjob.bootstrap.type.ScheduleJobBootstrap;
import org.apache.shardingsphere.elasticjob.reg.base.CoordinatorRegistryCenter;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperConfiguration;
import org.apache.shardingsphere.elasticjob.reg.zookeeper.ZookeeperRegistryCenter;
import org.apache.shardingsphere.elasticjob.script.props.ScriptJobProperties;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScriptJobManualTest {
    private static final Logger LOG = LoggerFactory.getLogger(ScriptJobManualTest.class);

    @Test
    void whenSchedulingAScriptJob_thenTheScriptRuns() throws Exception {
        JobConfiguration jobConfig = JobConfiguration.newBuilder("MyScriptJob", 3)
            .cron("0/5 * * * * ?")
            .jobParameter("Hello")
            .shardingItemParameters("0=a,1=b,2=c")
            .setProperty(ScriptJobProperties.SCRIPT_KEY, getFilename())
            .build();

        new ScheduleJobBootstrap(createRegistryCenter(), "SCRIPT", jobConfig)
            .schedule();

        // Keep the test alive indefinitely so that the job can run
        Thread.currentThread().join();
    }

    private static String getFilename() throws IOException {
        String filename;
        if (System.getProperties().getProperty("os.name").contains("Windows")) {
            filename = Paths.get("./scriptJob.bat").toAbsolutePath().toString();
        } else {
            Path result = Paths.get("./scriptJob.sh").toAbsolutePath();
            Files.setPosixFilePermissions(result, PosixFilePermissions.fromString("rwxr-xr-x"));

            filename = result.toString();
        }

        LOG.info("Script to run: {}", filename);
        return filename;
    }

    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("localhost:2181",
            ScriptJobManualTest.class.getName()));
        regCenter.init();
        return regCenter;
    }

}
