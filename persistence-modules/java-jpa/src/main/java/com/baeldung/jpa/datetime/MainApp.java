package com.baeldung.jpa.datetime;

public class MainApp {

    public static void main(String... args) {

        DateTimeEntityRepository dateTimeEntityRepository = new DateTimeEntityRepository();

        //Persist
        dateTimeEntityRepository.save(100L);

        //Find
        JPA22DateTimeEntity dateTimeEntity = dateTimeEntityRepository.find(100L);

        dateTimeEntityRepository.clean();
    }

}