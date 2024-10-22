package com.baeldung.mockito.deepstubs;

import java.util.ArrayList;
import java.util.List;

public class NewsAgency {
    List<Reporter> reporters;

    public NewsAgency(List<Reporter> reporters) {
        this.reporters = reporters;
    }

    public List<String> getLatestArticlesNames(){
        List<String> results = new ArrayList<>();
        for(Reporter reporter : this.reporters){
            results.add(reporter.getLatestArticle().getName());
        }
        return results;
    }
}
