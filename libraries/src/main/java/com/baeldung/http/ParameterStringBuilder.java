package com.baeldung.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        params.forEach((key, value) -> {
            try {
                result.append(URLEncoder.encode(key, "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(value, "UTF-8"));
                result.append("&");
            } catch (UnsupportedEncodingException exc) {
            }
        });

        String resultString = result.toString();
        if (resultString.length() > 0) {
            resultString = resultString.substring(0, resultString.length() - 1);
        }
        return resultString;
    }
}
