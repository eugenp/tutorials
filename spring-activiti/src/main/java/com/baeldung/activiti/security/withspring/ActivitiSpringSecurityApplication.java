package com.baeldung.activiti.security.withspring;

import org.activiti.engine.impl.persistence.entity.data.impl.MybatisGroupDataManager;
import org.activiti.engine.impl.persistence.entity.data.impl.MybatisUserDataManager;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.baeldung.activiti.security.config.SpringSecurityGroupManager;
import com.baeldung.activiti.security.config.SpringSecurityUserManager;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class, scanBasePackages = { "com.baeldung.activiti.security.config", "com.baeldung.activiti.security.withspring" })
public class ActivitiSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActivitiSpringSecurityApplication.class, args);
    }

    @Autowired
    private SpringProcessEngineConfiguration processEngineConfiguration;

    @Autowired
    private JdbcUserDetailsManager userManager;

    @Bean
    InitializingBean processEngineInitializer() {
        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {
                processEngineConfiguration.setUserEntityManager(new SpringSecurityUserManager(processEngineConfiguration, new MybatisUserDataManager(processEngineConfiguration), userManager));
                processEngineConfiguration.setGroupEntityManager(new SpringSecurityGroupManager(processEngineConfiguration, new MybatisGroupDataManager(processEngineConfiguration)));
            }
        };
    }
}
