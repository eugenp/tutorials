import java.util.HashMap;
import java.util.Map;

public class UseHashMapToConvertPhoneNumberInWordsToNumber {
    
    public static String convertPhoneNumberInWordToNumber(String phoneNumberInWord) {

        Map<String, Integer> mulpliers = getWordAsMultiplier();
        Map<String, String> digits = getWordAsDigit();

        StringBuilder output = new StringBuilder();
        Integer currentMultiplier = null;
        String[] words = phoneNumberInWord.split(" ");

        for (String word : words) {
            Integer multiplier = mulpliers.get(word);
            if (multiplier != null) {
                if (currentMultiplier != null) {
                    throw new IllegalArgumentException("Cannot have consecutive multipliers, at: " + word);
                }
                currentMultiplier = multiplier;
            } else {
                String digit = digits.get(word);
                if (digit != null) {
                    output.append(digit.repeat(currentMultiplier != null ? currentMultiplier : 1));
                    currentMultiplier = null;
                } else {
                    throw new IllegalArgumentException("Invalid word: " + word);
                }
            }
        }

        return output.toString();
    }

    public static Map<String, Integer> getWordAsMultiplier() {
        Map<String, Integer> multiplier = new HashMap<>();

        multiplier.put("double", 2);
        multiplier.put("triple", 3);
        multiplier.put("quadruple", 4);

        return multiplier;
    }


    public static Map<String, String> getWordAsDigit() {
        Map<String, String> digits = new HashMap<>();

        digits.put("zero", "0");
        digits.put("one", "1");
        digits.put("two", "2");
        digits.put("three", "3");
        digits.put("four", "4");
        digits.put("five", "5");
        digits.put("six", "6");
        digits.put("seven", "7");
        digits.put("eight", "8");
        digits.put("nine", "9");

        return digits;
    }
}
