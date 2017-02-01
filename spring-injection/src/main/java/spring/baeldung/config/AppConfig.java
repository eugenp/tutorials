package spring.baeldung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import spring.baeldung.ConstructorInjectionDemo;
import spring.baeldung.DependencyA;
import spring.baeldung.DependencyB;
import spring.baeldung.FieldInjectionDemo;

@Configuration
public class AppConfig {

    @Bean(name="dependencyA")
    public DependencyA dependencyA() {
        return new DependencyA();
    }

    @Bean(name="dependencyB")
    public DependencyB dependencyB() {
        return new DependencyB();
    }
    
    @Bean(name="fieldInjectionDemo")
    public FieldInjectionDemo fieldInjectionDemo() {
        return new FieldInjectionDemo();
    }
    
    @Bean(name="constructorInjectionDemo")
    public ConstructorInjectionDemo constructorInjectionDemo() {
        return new ConstructorInjectionDemo(dependencyA(), dependencyB());
    }
}