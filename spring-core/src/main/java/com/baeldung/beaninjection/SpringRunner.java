package com.baeldung.beaninjection;

import com.baeldung.beaninjection.domain.Hero;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringRunner {

    private Hero hero;

    public SpringRunner(Hero hero) {
        this.hero = hero;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringRunner.class, args);
    }

    @PostConstruct
    public void showHero(){
        System.out.println(hero);
    }
}
