package com.baeldung.finduniqueemails;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindUniqueEmailsUnitTest {
    String[] emailList = {"user@example.com", "user@example.com", "user@gmail.com", "admin@example.com", "USER@example.com"};
    Set<String> expectedUniqueEmails = new HashSet<>();

    FindUniqueEmailsUnitTest() {
        expectedUniqueEmails.add("user@example.com");
        expectedUniqueEmails.add("user@gmail.com");
        expectedUniqueEmails.add("admin@example.com");
    }

    @Test
    public void givenEmailList_whenUsingBasicStringManipulation_thenFindUniqueEmails() {
        Set<String> uniqueEmails = new HashSet<>();

        for (String email : emailList) {
            String[] parts = email.split("@");
            if (parts.length == 2) {
                uniqueEmails.add(parts[0].toLowerCase() + "@" + parts[1].toLowerCase());
            }
        }

        assertEquals(expectedUniqueEmails, uniqueEmails);
    }

    @Test
    public void givenEmailList_whenUsingJavaStreams_thenFindUniqueEmails() {

        Set<String> uniqueEmails = Arrays.stream(emailList)
                .map(email -> email.split("@"))
                .filter(parts -> parts.length == 2)
                .map(parts -> parts[0].toLowerCase() + "@" + parts[1])
                .collect(Collectors.toSet());

        assertEquals(expectedUniqueEmails, uniqueEmails);
    }
}