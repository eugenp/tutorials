package whatsnew.records;

/**
 * Java record with a header indicating 2 fields.
 */
public record Person(String name, int age) {

    /**
     * Public constructor that does some basic validation.
     */
    public Person {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }
}
