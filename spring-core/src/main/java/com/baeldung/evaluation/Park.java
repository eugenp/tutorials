package com.baeldung.evaluation;

public class Park {
    // setter injection
    private Search searcher;

    private Map map;
    
    public Map getMap() {
        return map;
    }

    private String name;

    // constructor injection
    public Park(Map map) {
        this.map = map;
    }

    public void searchMap() {
        System.out.println("searching map with " + searcher.getName() + " in " + map.getName());
    }

    public Search getSearcher() {
        return searcher;
    }

    public void setSearcher(Search searcher) {
        this.searcher = searcher;
    }

    // Standard setters and getters

}
