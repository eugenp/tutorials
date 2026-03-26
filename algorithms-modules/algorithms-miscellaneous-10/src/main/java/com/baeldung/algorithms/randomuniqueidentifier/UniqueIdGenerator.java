package com.baeldung.algorithms.randomuniqueidentifier;

import java.security.SecureRandom;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Custom Random Identifier generator with unique ids .
 */
public class UniqueIdGenerator {

    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();
    
    private int idLength = 8; // Default length

    /**
     * Overrides the default ID length for generated identifiers.
     * @param idLength The desired length (must be positive).
     */
    public void setIdLength(int idLength) {
        if (idLength <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        this.idLength = idLength;
    }

    /**
     * Generates a unique alphanumeric ID using the SecureRandom character mapping approach.
     * @param existingIds A set of IDs already in use to ensure uniqueness.
     * @return A unique alphanumeric string.
     */
    public String generateUniqueId(Set<String> existingIds) {
        String newId;
        do {
            newId = generateRandomString(this.idLength);
        } while (existingIds.contains(newId));
        return newId;
    }

    private String generateRandomString(int length) {
        return random.ints(length, 0, ALPHANUMERIC.length())
          .mapToObj(ALPHANUMERIC::charAt)
          .map(Object::toString)
          .collect(Collectors.joining());
    }
}

