public class UseSwitchToConvertPhoneNumberInWordsToNumber {

    public static String convertPhoneNumberInWordsToNumber(String phoneNumberInWord) {

        StringBuilder output = new StringBuilder();
        Integer currentMultiplier = null;
        String[] words = phoneNumberInWord.split(" ");

        for (String word : words) {
            Integer multiplier = getWordAsMultiplier(word);
            if (multiplier != null) {
                if (currentMultiplier != null) {
                    throw new IllegalArgumentException("Cannot have consecutive multipliers, at: " + word);
                }
                currentMultiplier = multiplier;
            } else {
                output.append(getWordAsDigit(word).repeat(currentMultiplier != null ? currentMultiplier : 1));
                currentMultiplier = null;
            }
        }
        return output.toString();
    }

    public static Integer getWordAsMultiplier(String word) {
        switch (word) {
            case "double":
                return 2;
            case "triple":
                return 3;
            case "quadruple":
                return 4;
            default:
                return null;
        }
    }

    public static String getWordAsDigit(String word) {
        switch (word) {
            case "zero":
                return "0";
            case "one":
                return "1";
            case "two":
                return "2";
            case "three":
                return "3";
            case "four":
                return "4";
            case "five":
                return "5";
            case "six":
                return "6";
            case "seven":
                return "7";
            case "eight":
                return "8";
            case "nine":
                return "9";
            default:
                throw new IllegalArgumentException("Invalid word: " + word);
        }
    }

}