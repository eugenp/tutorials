package com.baeldung.paymentservice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardValidator {

    public static boolean validate(String cardNumber){

        boolean isValid = false;

        String regex = "^(?:(?<visa>4[0-9]{12}(?:[0-9]{3})?)|" +
                "(?<mastercard>5[1-5][0-9]{14})|" +
                "(?<discover>6(?:011|5[0-9]{2})[0-9]{12})|" +
                "(?<amex>3[47][0-9]{13})|" +
                "(?<diners>3(?:0[0-5]|[68][0-9])?[0-9]{11})|" +
                "(?<jcb>(?:2131|1800|35[0-9]{3})[0-9]{11}))$";


        Pattern pattern = Pattern.compile(regex);
        cardNumber = cardNumber.replaceAll("-", "");
        Matcher matcher = pattern.matcher(cardNumber);

        if(matcher.matches()){

            isValid = true;
        }

        return isValid;
    }
}
