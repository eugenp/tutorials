package com.baeldung.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!dev & !production & !mysql & !tomcat")
public class SpecialDatasourceConfig implements DatasourceConfig {

    @Override
    public void setup() {
        System.out.println("Setting up a very special datasource. ");
    }

}