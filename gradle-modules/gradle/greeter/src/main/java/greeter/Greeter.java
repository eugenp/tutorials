package greeter;

import baeldunggreeter.Formatter;

public class Greeter {
    public static void main(String[] args) {
        final String output = GreetingFormatter
            .greeting(args[0]);
        String date = Formatter.getFormattedDate();
        System.out.println(output);
        System.out.println("Today is :" + date);
    }
}
