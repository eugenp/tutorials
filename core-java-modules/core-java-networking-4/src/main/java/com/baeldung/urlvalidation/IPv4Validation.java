package com.baeldung.urlvalidation;

import com.google.common.net.InetAddresses;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPv4Validation {

    public static boolean validateWithApacheCommons(String ip) {
        InetAddressValidator validator = InetAddressValidator.getInstance();
        return validator.isValid(ip);
    }

    public static boolean validateWithGuava(String ip) {
        return InetAddresses.isInetAddress(ip);
    }

    public static boolean validateWithRegex(String ip) {
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

}
