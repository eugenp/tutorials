public class StringComparisonDemo {

    static String i1 = "String";
    static String i2 = "String";
    static StringBuffer i3 = new StringBuffer("String");

    public static void main(final String[] args) {

        boolean isEqual = i1.contentEquals(i2);
        boolean isEqual2 = i1.equals(i3);

        System.out.println(isEqual);
        System.out.println(isEqual2);

        System.out.println(i2.hashCode());
        System.out.println(i1.hashCode());
        System.out.println(i3.hashCode());
    }
}
