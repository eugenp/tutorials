package com.baeldung.activiti.security.withactiviti;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = { "com.baeldung.activiti.security.config", "com.baeldung.activiti.security.withactiviti" })
public class SpringSecurityActivitiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityActivitiApplication.class, args);
    }

    @Bean
    InitializingBean usersAndGroupsInitializer(IdentityService identityService) {
        return new InitializingBean() {
            public void afterPropertiesSet() throws Exception {
                User user = identityService.newUser("activiti_user");
                user.setPassword("pass");
                identityService.saveUser(user);

                Group group = identityService.newGroup("user");
                group.setName("ROLE_USER");
                group.setType("USER");
                identityService.saveGroup(group);
                identityService.createMembership(user.getId(), group.getId());
            }
        };
    }
}
