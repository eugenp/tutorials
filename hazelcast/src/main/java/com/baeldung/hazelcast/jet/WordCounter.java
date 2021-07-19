package com.baeldung.hazelcast.jet;

import com.hazelcast.jet.Jet;
import com.hazelcast.jet.JetInstance;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.Sources;

import java.util.List;
import java.util.Map;

import static com.hazelcast.function.Functions.wholeItem;
import static com.hazelcast.jet.Traversers.traverseArray;
import static com.hazelcast.jet.aggregate.AggregateOperations.counting;

public class WordCounter {

    private static final String LIST_NAME = "textList";
    private static final String MAP_NAME = "countMap";

    private Pipeline createPipeLine() {
        Pipeline p = Pipeline.create();
        p.readFrom(Sources.<String>list(LIST_NAME))
            .flatMap(word -> traverseArray(word.toLowerCase().split("\\W+")))
            .filter(word -> !word.isEmpty())
            .groupingKey(wholeItem())
            .aggregate(counting())
            .writeTo(Sinks.map(MAP_NAME));
        return p;
    }

    public Long countWord(List<String> sentences, String word) {
        long count = 0;
        JetInstance jet = Jet.newJetInstance();
        try {
            List<String> textList = jet.getList(LIST_NAME);
            textList.addAll(sentences);
            Pipeline p = createPipeLine();
            jet.newJob(p).join();
            Map<String, Long> counts = jet.getMap(MAP_NAME);
            count = counts.get(word);
        } finally {
            Jet.shutdownAll();
        }
        return count;
    }

}
