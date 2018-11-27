package com.baeldung.synthetic;

/**
 * Wrapper for a class which contains a synthetic constructor.
 * 
 * @author Donato Rimenti
 *
 */
public class SyntheticConstructorDemo {

    /**
     * We need to instantiate the {@link NestedClass} using a private
     * constructor from the enclosing instance in order to generate a synthetic
     * constructor.
     */
    private NestedClass nestedClass = new NestedClass();

    /**
     * Class which contains a synthetic constructor.
     * 
     * @author Donato Rimenti
     *
     */
    class NestedClass {

        /**
         * In order to generate a synthetic constructor, this class must have a
         * private constructor.
         */
        private NestedClass() {
        }
    }

}