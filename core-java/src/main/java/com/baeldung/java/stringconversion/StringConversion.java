public class StringConversion {
    public static void main(String... args) {
        stringToCharArr();
        charArrToString();
    }

    public static void stringToCharArr() {
        final String testString = "JavaString";
        final char[] testStringCharArr = testString.toCharArray();
        for (int i = 0; i < testString.length(); i++) {
            if (testString.charAt(i) != testStringCharArr[i]) {
                System.out.println("Invalid StringToCharArray conversion");
                break;
            }
        }
    }
    
    public static void charArrToString() {
        final char[] testStringCharArr = {'J', 'a', 'v', 'a', 'S', 't', 'r', 'i', 'n', 'g'};
        final String testString = new String(testStringCharArr);
        for (int i = 0; i < testString.length(); i++) {
            if (testString.charAt(i) != testStringCharArr[i]) {
                System.out.println("Invalid CharArrayToString conversion");
                break;
            }
        }
    }
}
