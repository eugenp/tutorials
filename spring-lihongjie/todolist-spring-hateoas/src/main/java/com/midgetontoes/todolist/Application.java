package com.midgetontoes.todolist;

import com.midgetontoes.todolist.jpa.ItemRepository;
import com.midgetontoes.todolist.jpa.ListRepository;
import com.midgetontoes.todolist.jpa.UserRepository;
import com.midgetontoes.todolist.model.Item;
import com.midgetontoes.todolist.model.List;
import com.midgetontoes.todolist.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

import java.util.Arrays;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableHypermediaSupport(type= {HypermediaType.HAL})
public class Application {

    @Bean
    CommandLineRunner init(UserRepository userRepository, ListRepository listRepository, ItemRepository itemRepository) {
        return (evt) -> Arrays.asList("alex", "bob", "david")
                .forEach(username -> {
                    User user = userRepository.save(new User(username, "password"));
                    List list =  listRepository.save(new List("Default", user));
                    itemRepository.save(new Item("My first item.", list));
                });
    }

    @Bean
    public CurieProvider curieProvider() {
        return new DefaultCurieProvider("todo",
                new UriTemplate("http://www.midgetontoes.com/todolist/rels/{rel}"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
