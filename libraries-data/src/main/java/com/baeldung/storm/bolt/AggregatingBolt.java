package com.baeldung.storm.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class AggregatingBolt extends BaseWindowedBolt {
    private OutputCollector outputCollector;
    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.outputCollector = collector;
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("sumOfOperations", "beginningTimestamp", "endTimestamp"));
    }

    @Override
    public void execute(TupleWindow tupleWindow) {
        List<Tuple> tuples = tupleWindow.get();
        tuples.sort(Comparator.comparing(a -> a.getLongByField("timestamp")));
        //This is safe since the window is calculated basing on Tuple's timestamp, thus it can't really be empty
        Long beginningTimestamp = tuples.get(0).getLongByField("timestamp");
        Long endTimestamp = tuples.get(tuples.size() - 1).getLongByField("timestamp");
        int sumOfOperations = tuples.stream().mapToInt(tuple -> tuple.getIntegerByField("operation")).sum();
        Values values = new Values(sumOfOperations, beginningTimestamp, endTimestamp);
        outputCollector.emit(values);
    }
}
