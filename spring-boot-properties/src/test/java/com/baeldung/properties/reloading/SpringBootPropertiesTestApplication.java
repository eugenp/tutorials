package com.baeldung.properties.reloading;

import com.baeldung.properties.reloading.beans.ValueRefreshConfigBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootApplication
@WebAppConfiguration
public class SpringBootPropertiesTestApplication {

    @Bean("singletonValueRefreshConfigBean")
    @RefreshScope
    @Scope("singleton")
    public ValueRefreshConfigBean singletonValueRefreshConfigBean(@Value("${application.theme.color:null}") String val) {
        return new ValueRefreshConfigBean(val);
    }

    @Bean
    @RefreshScope
    public ValueRefreshConfigBean valueRefreshConfigBean(@Value("${application.theme.color:null}") String val) {
        return new ValueRefreshConfigBean(val);
    }

}
