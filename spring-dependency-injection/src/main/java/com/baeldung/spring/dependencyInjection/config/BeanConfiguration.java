package com.baeldung.spring.dependencyInjection.config;

import com.baeldung.spring.dependencyInjection.beans.Content;
import com.baeldung.spring.dependencyInjection.beans.DependencyBean;
import com.baeldung.spring.dependencyInjection.beans.MrBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public Content createContent() {
        return new Content();
    }

    @Bean
    public DependencyBean createDependencyBean(Content content) {
        DependencyBean dependencyBean = new DependencyBean();
        dependencyBean.setContent(content);
        return dependencyBean;
    }

    @Bean
    public MrBean createMrBean(DependencyBean dependencyBean) {
        return new MrBean(dependencyBean);
    }

}
