package com.baeldung.listoflists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * List of lists code sample
 */
public class Launcher {

    public static void main(String[] args) {
        final List<Runner> runners = new ArrayList<>();

        runners.add(new Runner("John", Gender.MALE, 1998));
        runners.add(new Runner("William", Gender.MALE, 1998));
        runners.add(new Runner("Julia", Gender.FEMALE, 1994));
        runners.add(new Runner("David", Gender.MALE, 1997));
        runners.add(new Runner("Daniela", Gender.FEMALE, 1980));
        runners.add(new Runner("Sarah", Gender.FEMALE, 1995));
        runners.add(new Runner("James", Gender.MALE, 1991));

        // Group by gender using lists of lists
        final List<List<Runner>> runnersGroupedByGender = new ArrayList<>();
        runnersGroupedByGender.add(new ArrayList<>());                          // The sublist for male runners
        runnersGroupedByGender.add(new ArrayList<>());                          // The sublist for female runners
        runners.forEach(r -> {
            if(r.getGender() == Gender.MALE) {
                runnersGroupedByGender.get(0).add(r);
            } else if (r.getGender() == Gender.FEMALE) {
                runnersGroupedByGender.get(1).add(r);
            }
        });

        System.out.println("First female runner: " + runnersGroupedByGender.get(1).get(0).getName());

        // Alternative version using Collectors.groupingBy()
        final Map<Gender, List<Runner>> genderListMap = runners.stream().collect(Collectors.groupingBy(Runner::getGender));

        System.out.println("First female runner: " + genderListMap.get(Gender.FEMALE).get(0).getName());

        // Slicing the list into smaller sublists
        final List<List<Runner>> slicedList = new ArrayList<>();
        final int subListSize = 3;
        int idx;
        for(idx = 0; idx < runners.size() - subListSize; idx += subListSize) {
            slicedList.add(new ArrayList<>(runners.subList(idx, idx + subListSize)));
        }
        if(runners.size() - idx > 0) {
            slicedList.add(new ArrayList<>(runners.subList(idx, runners.size())));
        }

        for(List<Runner> subList : slicedList) {
            System.out.println(subList.size());
        }
    }

}
