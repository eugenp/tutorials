package com.baeldung.annotations;

class ClassWithDeprecatedMethod {

    @Deprecated
    // Java 9 example of @Deprecated
    // @Deprecated(since = "4.5", forRemoval = true)
    static void deprecatedMethod() {

    }
}