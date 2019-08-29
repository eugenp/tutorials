package com.baeldung.enumset;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class EnumSets {

    public enum Color {
        RED, YELLOW, GREEN, BLUE, BLACK, WHITE
    }

    public static void main(String[] args) {
        EnumSet<Color> allColors = EnumSet.allOf(Color.class);
        System.out.println(allColors);

        EnumSet<Color> noColors = EnumSet.noneOf(Color.class);
        System.out.println(noColors);

        EnumSet<Color> blackAndWhite = EnumSet.of(Color.BLACK, Color.WHITE);
        System.out.println(blackAndWhite);

        EnumSet<Color> noBlackOrWhite = EnumSet.complementOf(blackAndWhite);
        System.out.println(noBlackOrWhite);

        EnumSet<Color> range = EnumSet.range(Color.YELLOW, Color.BLUE);
        System.out.println(range);

        EnumSet<Color> blackAndWhiteCopy = EnumSet.copyOf(EnumSet.of(Color.BLACK, Color.WHITE));
        System.out.println(blackAndWhiteCopy);

        List<Color> colorsList = new ArrayList<>();
        colorsList.add(Color.RED);
        EnumSet<Color> listCopy = EnumSet.copyOf(colorsList);
        System.out.println(listCopy);

        EnumSet<Color> set = EnumSet.noneOf(Color.class);
        set.add(Color.RED);
        set.add(Color.YELLOW);
        set.contains(Color.RED);
        set.forEach(System.out::println);
        set.remove(Color.RED);
    }

}
