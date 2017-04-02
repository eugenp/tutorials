package com.baeldung.di;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.baeldung.di.model.BookConstructor;
import com.baeldung.di.model.BookSetter;

public class SpringRunner {
    public static void main(String[] args) {
        BookSetter spring = getBookSetterConfig();

        System.out.println("Setter DI [" +spring +"]");
        
        BookConstructor java = getBookConstructorConfig();

        System.out.println("Constructor DI [" + java +"]");
    }

    private static BookSetter getBookSetterConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DiConfig.class);

        return context.getBean(BookSetter.class);
    }
    
    private static BookConstructor getBookConstructorConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DiConfig.class);

        return context.getBean(BookConstructor.class);
    }
}
