package com.baeldung;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 测试：生成随机数
 * 方法：
 * （1）{@link Random#nextInt(int bound)}}
 * （2）{@link Math#random()}
 * （3）{@link ThreadLocalRandom#current()#nextInt(10) }
 */
public class RandomListElementUnitTest {

    @Test
    public void givenList_whenRandomIndexChosen_shouldReturnARandomElementUsingRandom() {
        List<Integer> givenList = Lists.newArrayList(1, 2, 3);
        Random rand = new Random();

        Integer element = givenList.get(rand.nextInt(givenList.size()));
        System.out.println("element:{}" + element);
    }

    /**
     * 注意：Math.random()生成0～1之间的小数
     */
    @Test
    public void givenList_whenRandomIndexChosen_shouldReturnARandomElementUsingMathRandom() {
        List<Integer> givenList = Lists.newArrayList(1, 2, 3);
        System.out.println("Math.random():{}" + Math.random());

        Integer element = givenList.get((int) (Math.random() * givenList.size()));
        System.out.println("element:{}" + element);
    }

    @Test
    public void givenList_whenNumberElementsChosen_shouldReturnRandomElementsRepeat() {
        Random rand = new Random();
        List<String> givenList = Lists.newArrayList("one", "two", "three", "four");

        int numberOfElements = 2;

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            String element = givenList.get(randomIndex);
            System.out.println("element:{}" + element);
        }
    }

    @Test
    public void givenList_whenNumberElementsChosen_shouldReturnRandomElementsNoRepeat() {
        Random rand = new Random();
        List<String> givenList = Lists.newArrayList("one", "two", "three", "four");

        int numberOfElements = 2;

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(givenList.size());
            String element = givenList.get(randomIndex);
            System.out.println("element" + element);
            givenList.remove(randomIndex);
        }
    }

    @Test
    public void givenList_whenSeriesLengthChosen_shouldReturnRandomSeries() {
        List<Integer> givenList = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        Collections.shuffle(givenList);
        System.out.println("givenList:{}" + givenList);

        int randomSeriesLength = 3;

        List<Integer> subList = givenList.subList(0, randomSeriesLength - 1);
        System.out.println("subList:{}" + subList);
    }

    @Test
    public void givenList_whenRandomIndexChosen_shouldReturnElementThreadSafely() {
        List<Integer> givenList = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        int randomIndex = ThreadLocalRandom.current().nextInt(10) % givenList.size();
        System.out.println("randomIndex:{}" + randomIndex);

        Integer element = givenList.get(randomIndex);
        System.out.println("element:{}" + element);
    }

}
