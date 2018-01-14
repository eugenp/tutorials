package com.baeldung.di.example.store;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "musicCD")
    public MusicCD getMusicCD() {
        return new MusicCD();
    }

    @Bean(name = "book")
    public Book getBook() {
        return new Book();
    }

    @Bean(name = "store")
    public Store getStoreCD() {
        Store store = new Store();
        store.setSell(getMusicCD());
        return store;
    }

    @Bean(name = "storeBook")
    public Store getStoreBook() {
        Store store = new Store();
        store.setSell(getBook());
        return store;
    }

    @Bean(name = "storeConstructorDI")
    public Store getStoreConstructor() {
        Store store = new Store(getMusicCD());
        return store;
    }
}
