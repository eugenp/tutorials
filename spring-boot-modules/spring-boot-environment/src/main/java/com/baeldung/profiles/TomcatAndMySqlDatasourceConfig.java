package com.baeldung.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("tomcat & mysql")
public class TomcatAndMySqlDatasourceConfig implements DatasourceConfig {

    @Override
    public void setup() {
        System.out.println("Setting up MySql datasource for Tomcat environment. ");
    }

}