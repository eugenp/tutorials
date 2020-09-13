package com.baeldung.halbrowser.config;

import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.baeldung.halbrowser.data.BookRepository;
import com.baeldung.halbrowser.model.Book;

@Component
public class DBLoader implements ApplicationRunner {

  private final BookRepository bookRepository;

  @Autowired
  DBLoader(BookRepository bookRepository){
    this.bookRepository = bookRepository;
  }


  public void run(ApplicationArguments applicationArguments) throws Exception {

     String[] templates = {
       "Up and running with %s",
       "%s Basics",
       "%s for Beginners",
       "%s for Neckbeards",
       "Under the hood: %s",
       "Discovering %s",
       "A short guide to %s",
       "%s with Baeldung"
     };

     String[] buzzWords = {
       "Spring REST Data",
       "Java 9",
       "Scala",
       "Groovy",
       "Hibernate",
       "Spring HATEOS",
       "The HAL Browser",
       "Spring webflux",
     };

     String[] authorFirstName = {
       "John %s",
       "Steve %s",
       "Samantha %s",
       "Gale %s",
       "Tom %s"
     };

     String[] authorLastName = {
       "Giles",
       "Gill",
       "Smith",
       "Armstrong"
     };

     String[] blurbs = {
       "It was getting dark when the %s %s" ,
       "Scott was nearly there when he heard that a %s %s",
       "Diana was a lovable Java coder until the %s %s",
       "The gripping story of a small %s and the day it %s"
     };

     String[] blublMiddles = {
       "distaster",
       "dog",
       "cat",
       "turtle",
       "hurricane"
     };

     String[] end = {
       "hit the school",
       "memorised pi to 100 decimal places!",
       "became a world champion armwrestler",
       "became a Java REST master!!"
     };

     Random random = new Random();

     IntStream.range(0, 100)
       .forEach(i -> {
         String template = templates[i % templates.length];
         String buzzword = buzzWords[i % buzzWords.length];
         String blurbStart = blurbs[i % blurbs.length];
         String middle = blublMiddles[i % blublMiddles.length];
         String ending = end[i % end.length];
         String blurb = String.format(blurbStart, middle, ending);
         String firstName = authorFirstName[i % authorFirstName.length];
         String lastname = authorLastName[i % authorLastName.length];
         Book book = new Book(String.format(template, buzzword), String.format(firstName, lastname), blurb, random.nextInt(1000-200) + 200);

         bookRepository.save(book);

         System.out.println(book);

       });



  }
}
