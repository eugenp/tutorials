package com.baeldung.apache.curator.modeled;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.x.async.AsyncCuratorFramework;
import org.apache.curator.x.async.modeled.JacksonModelSerializer;
import org.apache.curator.x.async.modeled.ModelSpec;
import org.apache.curator.x.async.modeled.ModeledFramework;
import org.apache.curator.x.async.modeled.ZPath;
import org.junit.Test;

import com.baeldung.apache.curator.BaseManualTest;

public class ModelTypedExamplesManualTest extends BaseManualTest {

    @Test
    public void givenPath_whenStoreAModel_thenNodesAreCreated()
        throws InterruptedException {

        ModelSpec<HostConfig> mySpec = ModelSpec
            .builder(ZPath.parseWithIds("/config/dev"),
                JacksonModelSerializer.build(HostConfig.class))
            .build();

        try (CuratorFramework client = newClient()) {
            client.start();
            AsyncCuratorFramework async = AsyncCuratorFramework.wrap(client);
            ModeledFramework<HostConfig> modeledClient = ModeledFramework
                .wrap(async, mySpec);

            modeledClient.set(new HostConfig("host-name", 8080));

            modeledClient.read()
                .whenComplete((value, e) -> {
                    if (e != null) {
                        fail("Cannot read host config", e);
                    } else {
                        assertThat(value).isNotNull();
                        assertThat(value.getHostname()).isEqualTo("host-name");
                        assertThat(value.getPort()).isEqualTo(8080);
                    }

                });
        }

    }
}
