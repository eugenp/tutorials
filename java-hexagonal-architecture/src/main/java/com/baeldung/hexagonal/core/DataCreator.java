package com.baeldung.hexagonal.core;

public class DataCreator {

        public static String createMockData() {
                return "[\n" + "\t{\n" + "\t\t\"name\": \"John Smith\",\n" + "\t\t\"phoneNumber\": \"123 456 789\"\n" + "\t},\n" + "\t{\n" + "\t\t\"name\": \"James Johnson\",\n" + "\t\t\"phoneNumber\": \"321 654 9879\"\n" + "\t},\n" + "\t{\n"
                    + "\t\t\"name\": \"Robert McCarry\",\n" + "\t\t\"phoneNumber\": \"654 456 789\"\n" + "\t}\n" + "]";
        }
}
