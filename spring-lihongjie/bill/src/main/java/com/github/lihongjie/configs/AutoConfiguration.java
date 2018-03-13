package com.github.lihongjie.configs;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan("com.github.lihongjie")
/*@PropertySource("classpath:application.properties")*/
/*@EnableJpaRepositories("com.github.lihongjie.repository")*/
/*@Import({
        JpaConfiguration.class

})*/
public class AutoConfiguration {


}
