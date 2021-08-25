package com.hexagon.hexagon_architecture.Framework.Config;

import com.hexagon.hexagon_architecture.Domain.Ports.UserRepository;
import com.hexagon.hexagon_architecture.Domain.Ports.UserService;
import com.hexagon.hexagon_architecture.Domain.Service.UserServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = UserServiceImpl.class)
public class BeanConfig {
    
    @Bean
    UserService userService(UserRepository userRepository){
        return new UserServiceImpl(userRepository);
    }
}
