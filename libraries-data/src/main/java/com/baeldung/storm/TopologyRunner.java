package com.baeldung.storm;

import com.baeldung.storm.bolt.AggregatingBolt;
import com.baeldung.storm.bolt.FileWritingBolt;
import com.baeldung.storm.bolt.FilteringBolt;
import com.baeldung.storm.bolt.PrintingBolt;
import com.baeldung.storm.spout.RandomNumberSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;

public class TopologyRunner {
    public static void main(String[] args) {
       runTopology();
    }

    public static void runTopology() {
        String filePath = "./src/main/resources/operations.txt";
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("randomNumberSpout", new RandomNumberSpout());
        builder.setBolt("filteringBolt", new FilteringBolt()).shuffleGrouping("randomNumberSpout");
        builder.setBolt("aggregatingBolt", new AggregatingBolt()
          .withTimestampField("timestamp")
          .withLag(BaseWindowedBolt.Duration.seconds(1))
          .withWindow(BaseWindowedBolt.Duration.seconds(5))).shuffleGrouping("filteringBolt");
        builder.setBolt("fileBolt", new FileWritingBolt(filePath)).shuffleGrouping("aggregatingBolt");

        Config config = new Config();
        config.setDebug(false);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("Test", config, builder.createTopology());
    }
}
