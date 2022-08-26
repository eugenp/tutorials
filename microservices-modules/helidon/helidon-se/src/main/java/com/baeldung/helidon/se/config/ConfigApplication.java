package com.baeldung.helidon.se.config;

import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import io.helidon.config.spi.ConfigSource;

public class ConfigApplication {

    public static void main(String... args) throws Exception {

        ConfigSource configSource = ConfigSources.classpath("application.yaml").build();
        Config config = Config.builder()
                .disableSystemPropertiesSource()
                .disableEnvironmentVariablesSource()
                .sources(configSource)
                .build();

        int port = config.get("server.port").asInt();
        int pageSize = config.get("web.page-size").asInt();
        boolean debug = config.get("web.debug").asBoolean();
        String userHome = config.get("user.home").asString();

        System.out.println("port: " + port);
        System.out.println("pageSize: " + pageSize);
        System.out.println("debug: " + debug);
        System.out.println("userHome: " + userHome);
    }

}
