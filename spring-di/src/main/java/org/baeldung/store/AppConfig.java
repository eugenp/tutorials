package org.baeldung.store;

import org.springframework.context.annotation.Bean;

public class AppConfig {

    @Bean
    public Item item1() {
        return new ItemImpl1();
    }
 
    @Bean
    public Store storeThroughConstructorInjection() {
        return new Store(item1());
    }
    
    @Bean
    public Store storeThroughSetterInjection() {
        Store store = new Store();
        store.setItem(item1());
        return store;
    }
}
