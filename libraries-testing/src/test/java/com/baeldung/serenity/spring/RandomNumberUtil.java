package com.baeldung.serenity.spring;

import org.apache.commons.lang3.RandomUtils;

/**
 * @author aiet
 */
public class RandomNumberUtil {

    public static int randomInt() {
        return RandomUtils.nextInt(1, 10);
    }
}
