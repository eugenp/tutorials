package com.baeldung.staticclass;

public class TopLevelClass {

    private static String privateStaticFieldInEnclosingClass;
    private String privateNonStaticFieldInEnclosingClass;

    TopLevelClass() {
        System.out.println("Value of non private static field of static class is " + PublicStaticClass.publicStaticFieldinStaticClass);
        System.out.println("Value of private static field of static class is " + PublicStaticClass.privateStaticFieldInStaticClass);
    }

    public static void main(String[] a) {
        // Create instance of the static class without an instance of enclosing class
        new TopLevelClass.PublicStaticClass();
        new TopLevelClass.DefaultStaticClass();
        new TopLevelClass.ProtectedStaticClass();
        new TopLevelClass.PrivateStaticClass();
    }

    // Accessible globally
    public static class PublicStaticClass {

        private static String privateStaticFieldInStaticClass;
        public static String publicStaticFieldinStaticClass;

        PublicStaticClass() {
            System.out.println("Value of static field of enclosing class is " + TopLevelClass.privateStaticFieldInEnclosingClass);
            System.out.println("Value of non-static field of enclosing class is " + new TopLevelClass().privateNonStaticFieldInEnclosingClass);
        }
    }

    // Accessible within the same package i.e com.baeldung.staticclass and from sub-classes in any package
    protected static class ProtectedStaticClass {
    }

    // Accessible within the same package i.e com.baeldung.staticclass
    static class DefaultStaticClass {
    }

    // Accessible only within TopLevelClass
    private static class PrivateStaticClass {
    }

}
