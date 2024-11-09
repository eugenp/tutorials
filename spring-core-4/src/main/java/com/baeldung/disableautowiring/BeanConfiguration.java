package com.baeldung.disableautowiring;
import com.example.Library;
import com.example.TestBean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    /** Uncomment this code and comment the below to fail this test case
     @Bean public TestBean testBean() {
     return Library.createBean();
     }
     **/

    @Bean
    public FactoryBean<TestBean> testBean() {
        return new TestBeanFactoryBean();
    }
}
