package com.baeldung.param;

import java.util.Arrays;

/**
 * Somewhat contrived validation class to illustrate unit test
 * concepts.
 * 
 * @author J Steven Perry
 *
 */
public class PersonValidator {

    /**
     * Contrived checked exception to illustrate one possible
     * way to handle validation errors (via a checked exception).
     * 
     * @author J Steven Perry
     *
     */
    public static class ValidationException extends Exception {

        /**
         * 
         */
        private static final long serialVersionUID = -134518049431883102L;

        // Probably should implement some more constructors, but don't want
        /// to tarnish the lesson...

        /**
         * The one and only way to create this checked exception.
         * 
         * @param message
         *          The message accompanying the exception. Should be meaningful.
         */
        public ValidationException(String message) {
            super(message);

        }

    }

    private static final String[] ILLEGAL_NAME_CHARACTERS = { ",", "_", "{", "}", "!" };

    /**
     * Validate the first name of the specified Person object.
     * 
     * @param person
     *          The Person object to validate.
     * 
     * @return - returns true if the specified Person is valid
     * 
     * @throws ValidationException
     *           - this Exception is thrown if any kind of validation error occurs.
     */
    public static boolean validateFirstName(Person person) throws ValidationException {
        boolean ret = true;
        // The validation rules go here.
        // Naive: use simple ifs
        if (person == null) {
            throw new ValidationException("Person is null (not allowed)!");
        }
        if (person.getFirstName() == null) {
            throw new ValidationException("Person FirstName is null (not allowed)!");
        }
        if (person.getFirstName()
            .isEmpty()) {
            throw new ValidationException("Person FirstName is an empty String (not allowed)!");
        }
        if (!isStringValid(person.getFirstName(), ILLEGAL_NAME_CHARACTERS)) {
            throw new ValidationException("Person FirstName (" + person.getFirstName() + ") may not contain any of the following characters: " + Arrays.toString(ILLEGAL_NAME_CHARACTERS) + "!");
        }
        return ret;
    }

    /**
     * Validate the last name of the specified Person object. Looks the same as first
     * name? Look closer. Just kidding. It's the same. But real world code can (and will) diverge.
     * 
     * @param person
     *          The Person object to validate.
     * 
     * @return - returns true if the specified Person is valid
     * 
     * @throws ValidationException
     *           - this Exception is thrown if any kind of validation error occurs.
     */
    public static boolean validateLastName(Person person) throws ValidationException {
        boolean ret = true;
        // The validation rules go here.
        // Naive: use simple ifs
        if (person == null) {
            throw new ValidationException("Person is null (not allowed)!");
        }
        if (person.getFirstName() == null) {
            throw new ValidationException("Person FirstName is null (not allowed)!");
        }
        if (person.getFirstName()
            .isEmpty()) {
            throw new ValidationException("Person FirstName is an empty String (not allowed)!");
        }
        if (!isStringValid(person.getFirstName(), ILLEGAL_NAME_CHARACTERS)) {
            throw new ValidationException("Person LastName (" + person.getLastName() + ") may not contain any of the following characters: " + Arrays.toString(ILLEGAL_NAME_CHARACTERS) + "!");
        }
        return ret;
    }

    /**
     * Validates the specified name. If it contains any of the illegalCharacters,
     * this method returns false (indicating the name is illegal). Otherwise it returns true.
     * 
     * @param candidate
     *          The candidate String to validate
     * 
     * @param illegalCharacters
     *          The characters the String is not allowed to have
     * 
     * @return - boolean - true if the name is valid, false otherwise.
     */
    private static boolean isStringValid(String candidate, String[] illegalCharacters) {
        boolean ret = true;
        for (String illegalChar : illegalCharacters) {
            if (candidate.contains(illegalChar)) {
                ret = false;
                break;
            }
        }
        return ret;
    }

}
