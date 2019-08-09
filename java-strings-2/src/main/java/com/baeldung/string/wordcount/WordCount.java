package com.baeldung.string.wordcount;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author shwetaJ
 */

public class WordCount {
    private static final Logger logger = LoggerFactory.getLogger(WordCount.class);

    static int countUsingSplit(String stringInput) {
        String delimiter = "[,:?.;~!@_|\\(\\)\\{\\}\\[|\\]|\\s]+";
        String stringTocheck = stringInput.trim();
        int counter = stringTocheck.split(delimiter).length;
        logger.info(" Total no of words using split are: " + counter + " delimiter by :" + delimiter);
        return counter;
    }

    static int countUsingStringTokenizer(String stringTocheck) {
        String delimiter = " ',-@;|!:[](){}_*#%^~.";
        StringTokenizer tokenizer = new StringTokenizer(stringTocheck, delimiter);
        int counter = tokenizer.countTokens();
        logger.info("Total no of words in StringTokenizer are: " + counter + " delimiter by :" + delimiter);
        return counter;
    }

    static int countUsingApacheStringUtils(String stringTocheck) {
        String delimiter = " ',-@;|!:[](){}_*#%^~.";
        int counter = StringUtils.split(stringTocheck, delimiter).length;
        logger.info("Total no of words in Apache String util are: " + counter + " delimiter by :" + delimiter);
        return counter;
    }

    /*
     *  Please include the spring-core dependency and then uncomment the below code to test Spring StringUtils.
     *  It is suggested that use this approach, only if, the project is already in spring framework
     */
    /* 
    static int countUsingSpringStringUtils(String stringTocheck) {
        String delimiter = " ',-@;|!:[](){}_*#%^~.";
        String[] counterStr = org.springframework.util.StringUtils.tokenizeToStringArray(stringTocheck, delimiter);
        int counter = counterStr.length;
        logger.info("Total no of words in Spring String util are: " + counter + " delimiter by :" + delimiter);
        return counter;
    }
    */
}
