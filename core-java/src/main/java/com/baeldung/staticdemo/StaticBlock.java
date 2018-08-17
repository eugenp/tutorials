package com.baeldung.staticdemo;

import java.util.LinkedList;
import java.util.List;

public class StaticBlock {
    private static List<String> ranks = new LinkedList<>();

    static {
        ranks.add("Lieutenant");
        ranks.add("Captain");
        ranks.add("Major");
    }
    
    static {
        ranks.add("Colonel");
        ranks.add("General");
    }

    //getters and setters
    public static List<String> getRanks() {
        return ranks;
    }

    public static void setRanks(List<String> ranks) {
        StaticBlock.ranks = ranks;
    }
}
