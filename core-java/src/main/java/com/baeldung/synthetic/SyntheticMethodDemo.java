package com.baeldung.synthetic;

/**
 * Wrapper for a class which contains two synthetic methods accessors to a
 * private field.
 * 
 * @author Donato Rimenti
 *
 */
public class SyntheticMethodDemo {

    /**
     * Class which contains two synthetic methods accessors to a private field.
     * 
     * @author Donato Rimenti
     *
     */
    class NestedClass {

        /**
         * Field for which will be generated synthetic methods accessors. It's
         * important that this field is private for this purpose.
         */
        private String nestedField;
    }

    /**
     * Gets the private nested field. We need to read the nested field in order
     * to generate the synthetic getter.
     * 
     * @return the {@link NestedClass#nestedField}
     */
    public String getNestedField() {
        return new NestedClass().nestedField;
    }

    /**
     * Sets the private nested field. We need to write the nested field in order
     * to generate the synthetic setter.
     * 
     * @param nestedField
     *            the {@link NestedClass#nestedField}
     */
    public void setNestedField(String nestedField) {
        new NestedClass().nestedField = nestedField;
    }

}