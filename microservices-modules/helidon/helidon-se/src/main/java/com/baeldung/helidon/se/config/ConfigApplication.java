package com.baeldung.helidon.se.config;

import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import io.helidon.config.spi.ConfigSource;

public class ConfigApplication {

    public static void main(String... args) {

        ConfigSource configSource = ConfigSources.classpath("application.yaml").build();
        Config config = Config.builder()
                .disableSystemPropertiesSource()
                .disableEnvironmentVariablesSource()
                .sources(configSource)
                .build();

        int port = config.get("server.port").asInt().get();
        int pageSize = config.get("web.page-size").asInt().get();
        boolean debug = config.get("web.debug").asBoolean().get();
        String userHome = config.get("user.home").asString().get();

        System.out.println("port: " + port);
        System.out.println("pageSize: " + pageSize);
        System.out.println("debug: " + debug);
        System.out.println("userHome: " + userHome);
    }

}
