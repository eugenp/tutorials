package com.baeldung.springintegration.config;

import com.baeldung.springintegration.dao.UserManagementDAO;
import com.baeldung.springintegration.dao.UserManagementDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCoreConfig {

    @Bean
    public UserManagementDAO userManagementDAO() {
        return new UserManagementDAOImpl();
    }
}
