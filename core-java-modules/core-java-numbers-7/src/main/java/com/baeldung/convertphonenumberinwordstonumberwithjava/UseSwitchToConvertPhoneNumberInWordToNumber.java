import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UseSwitchToConvertPhoneNumberInWordToNumber {

    public static String ConvertPhoneNumberInWordToNumber(String phoneNumberInWord) {

        boolean format = isValidPhoneNumberFormat(phoneNumberInWord);
        String convertedNumber = "", temp = "";


        if (format) {
            List<String> phoneNumberInWordArray = Arrays.asList(phoneNumberInWord.split(" "));

            for (int i = 0; i < phoneNumberInWordArray.size(); i++) {
                temp = mapModifiers(phoneNumberInWordArray.get(i));
                if (temp.equals("-1")) {
                    convertedNumber += mapIndividualDigits(phoneNumberInWordArray.get(i));
                } else {
                    convertedNumber += String.join("", Collections.nCopies(Integer.parseInt(temp), mapIndividualDigits(phoneNumberInWordArray.get(i + 1))));
                    i++;
                }
            }
        }


        if (convertedNumber.length() > 10) {
            System.out.println("Incorrect Phone Number! The correct one is 10-digit long.");
            return null;
        }

        return convertedNumber;

    }

    public static boolean isValidPhoneNumberFormat(String phoneNumberInWord) {

        String regex = "^(double|triple|quadruple|zero|one|two|three|four|five|six|seven|eight|nine)$";

        phoneNumberInWord = phoneNumberInWord.toLowerCase();

        if (phoneNumberInWord.endsWith("double") ||
                phoneNumberInWord.endsWith("triple") ||
                phoneNumberInWord.endsWith("quadruple")) {
            System.out.println("A Phone Number Can't End with a Numerical Prefix");
            return false;
        } else {
            List<String> phoneNumberInWordArray = Arrays.asList(phoneNumberInWord.split(" "));
            for (int i = 0; i < phoneNumberInWordArray.size(); i++) {
                if ((isNumericalPrefix(phoneNumberInWordArray.get(i)) && isNumericalPrefix(phoneNumberInWordArray.get(i + 1)))) {
                    System.out.println("Consecutive Numerical Prefixes Detected.");
                    return false;
                }
                if (!phoneNumberInWordArray.get(i).matches(regex)) {
                    System.out.println("Invalid Numberical Prefix or Digit in Words! Try again");
                    return false;
                }
            }

        }
        return true;
    }

    public static boolean isNumericalPrefix(String word) {
        return word.equals("double") || word.equals("triple") || word.equals("quadruple");
    }

    public static String mapModifiers(String word) {

        switch (word) {
            case "double":
                return "2";
            case "triple":
                return "3";
            case "quadruple":
                return "4";
            default:
                return "-1";
        }

    }

    public static String mapIndividualDigits(String word) {
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
                return "-1";
        }
    }
}
