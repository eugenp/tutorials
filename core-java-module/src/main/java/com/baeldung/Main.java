package com.baeldung;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        // Create some Article instances
        Article article1 = new Article("Tech Trends in Java", "Latest updates in JDK21...");
        Article article2 = new Article("Project Loom Java", "Benefits of using Virtual Threads...");

        // Create a Baeldung instance and add articles
        Baeldung originalWebsite = new Baeldung(Arrays.asList(article1, article2));
        originalWebsite.addArticle(article1);
        originalWebsite.addArticle(article2);

        // Perform Shallow Copy using Cloneable
        Baeldung shallowCopy = (Baeldung) originalWebsite.clone();

        Baeldung deepCopy = Baeldung.createDeepCopy(originalWebsite);

        // Perform Deep Copy using Apache Commons Lang
        Baeldung deepCopyApache = org.apache.commons.lang3.SerializationUtils.clone(originalWebsite);

        // Perform Deep Copy using Gson
        com.google.gson.Gson gson = new com.google.gson.Gson();
        Baeldung deepCopyGson = gson.fromJson(gson.toJson(originalWebsite), Baeldung.class);

        // Perform Deep Copy using Jackson
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        Baeldung deepCopyJackson = null;
        try {
            deepCopyJackson = mapper.readValue(mapper.writeValueAsString(originalWebsite), Baeldung.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Print original and copied objects
        System.out.println("Original Website: " + originalWebsite);
        System.out.println("Shallow Copy: " + shallowCopy);
        System.out.println("Shallow Copy: " + deepCopy);
        System.out.println("Deep Copy (Apache Commons): " + deepCopyApache);
        System.out.println("Deep Copy (Gson): " + deepCopyGson);
        System.out.println("Deep Copy (Jackson): " + deepCopyJackson);
    }
}
