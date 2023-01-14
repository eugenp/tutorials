package com.baeldung.urlvalidation;

import com.google.common.net.InetAddresses;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.util.Arrays;
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

    private static boolean validateWithRegex(String ip) {
        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static boolean validateWithStream(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return false;
        }
        return Arrays.stream(parts).allMatch(s -> {
            try {
                int i = Integer.parseInt(s);
                return i >= 0 && i <= 255;
            } catch (NumberFormatException e) {
                return false;
            }
        });
    }

    public static void main(String[] args) {
        String ip = "127.0.0.1";
        System.out.println(validateWithApacheCommons(ip));
//        System.out.println(validateWithGuava(ip));
//        System.out.println(validateWithRegex(ip));
//        System.out.println(validateWithStream(ip));
    }
}
