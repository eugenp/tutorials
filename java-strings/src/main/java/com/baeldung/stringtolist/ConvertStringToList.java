package com.baeldung.stringtolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

public class ConvertStringToList {


    public List<Integer> stringToIntegerListUsingJava8Stream(String stringToConvert) {
        List<Integer> listOfInteger = Stream.of(stringToConvert.split(","))
            .map(String::trim)
            .map(Integer::parseInt)
            .collect(Collectors.toList());
        return listOfInteger;
    }

    public List<Integer> stringToIntegerListUsingGuava(String stringToConvert) {
        List<Integer> listOfInteger = Lists.transform(Splitter.on(",")
            .trimResults()
            .splitToList(stringToConvert), new Function<String, Integer>() {
                @Override
                public Integer apply(String input) {
                    return Integer.parseInt(input.trim());
                }
            });

        return listOfInteger;
    }

    public List<Integer> stringToIntegerListUsingApacheCommonsLang(String stringToConvert) {
        String[] arrayOfString = StringUtils.split(stringToConvert, ",");
        List<Integer> listOfInteger = new ArrayList<Integer>();
        for (String number : arrayOfString) {
            listOfInteger.add(Integer.parseInt(number.trim()));
        }
        return listOfInteger;
    }

    public List<Integer> stringToIntegerListInJava(String stringToConvert) {
        String[] arrayOfString = stringToConvert.split(",");
        List<Integer> listOfInteger = new ArrayList<Integer>();
        for (String number : arrayOfString) {
            listOfInteger.add(Integer.parseInt(number.trim()));
        }
        return listOfInteger;
    }

    public List<String> stringToStringListUsingJava8Stream(String stringToConvert) {
        List<String> listOfString = Stream.of(stringToConvert.split(",", -1))
            .collect(Collectors.toList());
        return listOfString;
    }

    public List<String> stringToStringListUsingGuava(String stringToConvert) {
        List<String> listOfString = Splitter.on(",")
            .trimResults()
            .splitToList(stringToConvert);
        return listOfString;
    }

    public List<String> stringToStringListUsingApacheCommonsLang(String stringToConvert) {
        List<String> listOfString = Arrays.asList(StringUtils.splitPreserveAllTokens(stringToConvert, ","));
        return listOfString;
    }

    public List<String> stringToStringListInJava(String stringToConvert) {
        List<String> listOfString = Arrays.asList(stringToConvert.split(",", -1));
        return listOfString;
    }

}
