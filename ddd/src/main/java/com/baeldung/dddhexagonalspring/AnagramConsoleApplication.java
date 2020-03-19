package com.baeldung.dddhexagonalspring;

import com.baeldung.dddhexagonalspring.application.cli.anagram.ConsoleAnagramAdaptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@ComponentScan(basePackages = { "com.baeldung.dddhexagonalspring.application.cli.anagram", "com.baeldung.dddhexagonalspring.domain.service.anagram" })
@Configuration
public class AnagramConsoleApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnagramConsoleApplication.class);

    @Autowired
    private ConsoleAnagramAdaptor anagramAdaptor;

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AnagramConsoleApplication.class);
        AnagramConsoleApplication application = context.getBean(AnagramConsoleApplication.class);
        application.start();
    }

    private void start() {
        LOGGER.info("AnagramConsoleApplication started.");
        LOGGER.info("Please enter two words...");
        Scanner scanner = new Scanner(System.in);
        String word1 = scanner.next();
        String word2 = scanner.next();
        boolean isAnagram = anagramAdaptor.isAnagram(word1, word2);
        if (isAnagram) {
            LOGGER.info("Words are anagram.");
        } else {
            LOGGER.info("Words are not anagram.");
        }
    }
}
