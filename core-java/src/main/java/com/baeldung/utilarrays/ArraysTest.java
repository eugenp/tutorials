package com.baeldung.utilarrays;

import java.util.Arrays;
import java.util.List;

public class ArraysTest {

    private static String[] storyIntro = new String[]{"once","upon","a","time"};

    public static void main(String[] args){
        testArrays();
        testArraysToList();
        testArraysTransformation();
        testArraysDeepEqualsDeepHash();
    }

    public static void testArrays(){

        String[] newStoryIntro = Arrays.copyOfRange(storyIntro, 0, 3);
        System.out.println(Arrays.toString(newStoryIntro));

        String[] copyNewIntro = Arrays.copyOf(newStoryIntro, 4);
        System.out.println(Arrays.toString(copyNewIntro));

        String[] fillIntro = new String[3];
        Arrays.fill(fillIntro, "once ");
        System.out.println(Arrays.toString(fillIntro));

        System.out.println(Arrays.equals(storyIntro, newStoryIntro));

        System.out.println(Arrays.equals(Arrays.copyOfRange(copyNewIntro, 0, 3), newStoryIntro));

        Arrays.sort(storyIntro);
        System.out.println(Arrays.toString(storyIntro));

        int index = Arrays.binarySearch(storyIntro, "time", String::compareToIgnoreCase);
        System.out.println("once " + "upon " + "a " + storyIntro[index]);
    }

    public static void testArraysToList(){
        List<String> rets = Arrays.asList(storyIntro);
        rets.stream().forEach(System.out::println);
    }
    public static void testArraysTransformation(){
        System.out.println(Arrays.toString(storyIntro));

        int hash = Arrays.hashCode(storyIntro);
        System.out.println("Hash before change: " + hash);

        storyIntro[3] = "foo";
        hash = Arrays.hashCode(storyIntro);
        System.out.println("Hash after change: " + hash);
    }

    public static void testArraysDeepEqualsDeepHash(){

        String[][] metaStoryIntro = new String[][]{storyIntro, storyIntro};
        int deepHash = Arrays.deepHashCode(metaStoryIntro);
        System.out.println("Deep Hash before change: " + deepHash);

        metaStoryIntro[1][3] = "foo";
        deepHash = Arrays.deepHashCode(metaStoryIntro);
        System.out.println("Deep Hash after change: " + deepHash);

        String[][] newMetaStoryIntro = new String[][]{storyIntro, storyIntro, storyIntro};
        newMetaStoryIntro[1][3] = "foo";
        System.out.println(Arrays.deepEquals(metaStoryIntro, newMetaStoryIntro));
    }
}