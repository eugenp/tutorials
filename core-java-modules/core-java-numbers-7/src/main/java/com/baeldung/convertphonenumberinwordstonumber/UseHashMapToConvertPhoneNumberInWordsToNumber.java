import java.util.Map;

public class UseHashMapToConvertPhoneNumberInWordsToNumber {
    private static Map<String, Integer> multipliers = Map.of("double",2,
            "triple", 3,
            "quadruple", 4);
    private static Map<String, String> digits = Map.of("zero","1",
            "one", "1",
            "two", "2",
            "three", "3",
            "four", "4",
            "five", "5",
            "six", "6",
            "seven", "7",
            "eight", "8",
            "nine", "9");


    public static String convertPhoneNumberInWordsToNumber(String phoneNumberInWord) {

        StringBuilder output = new StringBuilder();
        Integer currentMultiplier = null;
        String[] words = phoneNumberInWord.split(" ");

        for (String word : words) {
            Integer multiplier = multipliers.get(word);
            if (multiplier != null) {
                if (currentMultiplier != null) {
                    throw new IllegalArgumentException("Cannot have consecutive multipliers, at: " + word);
                }
                currentMultiplier = multiplier;
            } else {
                String digit = digits.get(word);
                if (digit == null) {
                    throw new IllegalArgumentException("Invalid word: " + word);
                }
                output.append(digit.repeat(currentMultiplier != null ? currentMultiplier : 1));
                currentMultiplier = null;
            }
        }
        return output.toString();
    }
}
