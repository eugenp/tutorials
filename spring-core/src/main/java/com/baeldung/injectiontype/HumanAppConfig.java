package com.baeldung.injectiontype;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

@Configurable
public class HumanAppConfig {

    @Bean
    public Action action() {
        return new Action();
    }

    @Bean
    public HumanService humanService() {
        HumanService humanService = new HumanService(action());
        humanService.setName("Sarf");
        return humanService;
    }

}