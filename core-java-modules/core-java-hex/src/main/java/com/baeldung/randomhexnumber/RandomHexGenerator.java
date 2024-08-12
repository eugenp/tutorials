package com.baeldung.randomhexnumber;

import java.security.SecureRandom;
import java.util.Random;

import org.apache.commons.math3.random.RandomDataGenerator;

public class RandomHexGenerator {

    public String generateUnboundedRandomHexUsingRandomNextInt() {
        Random random = new Random();
        int randomInt = random.nextInt();
        return Integer.toHexString(randomInt);
    }

    public String generateRandomHexUsingRandomNextIntWithInRange(int lower, int upper) {
        Random random = new Random();
        int randomInt = random.nextInt(upper - lower) + lower;
        return Integer.toHexString(randomInt);
    }

    public String generateRandomHexUsingSecureRandomNextInt() {
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt();
        return Integer.toHexString(randomInt);
    }

    public String generateRandomHexUsingSecureRandomNextIntWithInRange(int lower, int upper) {
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(upper - lower) + lower;
        return Integer.toHexString(randomInt);
    }

    public String generateUnboundedRandomHexUsingRandomNextLong() {
        Random random = new Random();
        long randomLong = random.nextLong();
        return Long.toHexString(randomLong);
    }

    public String generateRandomHexUsingSecureRandomNextLong() {
        SecureRandom secureRandom = new SecureRandom();
        long randomLong = secureRandom.nextLong();
        return Long.toHexString(randomLong);
    }

    public String generateRandomHexWithStringFormatter() {
        Random random = new Random();
        int randomInt = random.nextInt();
        return String.format("%02x", randomInt);
    }

    public String generateRandomHexWithCommonsMathRandomDataGenerator(int len) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        return randomDataGenerator.nextHexString(len);
    }

    public String generateSecureRandomHexWithCommonsMathRandomDataGenerator(int len) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        return randomDataGenerator.nextSecureHexString(len);
    }

    public String generateRandomHexWithCommonsMathRandomDataGeneratorNextIntWithRange(int lower, int upper) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        int randomInt = randomDataGenerator.nextInt(lower, upper);
        return Integer.toHexString(randomInt);
    }

    public String generateRandomHexWithCommonsMathRandomDataGeneratorSecureNextIntWithRange(int lower, int upper) {
        RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
        int randomInt = randomDataGenerator.nextSecureInt(lower, upper);
        return Integer.toHexString(randomInt);
    }

}
