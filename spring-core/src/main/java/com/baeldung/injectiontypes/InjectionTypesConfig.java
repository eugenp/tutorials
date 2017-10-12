package com.baeldung.injectiontypes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InjectionTypesConfig {

    @Bean
    public Dependency1 dep1() {
        return new Dependency1();
    }

    @Bean
    public Dependency2 dep2() {
        return new Dependency2();
    }

    @Bean
    public MyService myService(Dependency1 d1, Dependency2 d2) {
        return new MyService(d1, d2);
    }

    @Bean
    public MyService2 myService2(Dependency1 d1, Dependency2 d2) {
        return new MyService2(d1, d2);
    }

    @Bean
    public MyService3 myService3(Dependency1 dependency) {
        MyService3 result = new MyService3();
        result.setDependency(dependency);
        return result;
    }
}
