package com.baeldung.storm.bolt;

import com.baeldung.storm.model.AggregatedWindow;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileWritingBolt extends BaseRichBolt {
    public static Logger logger = LoggerFactory.getLogger(FileWritingBolt.class);
    private BufferedWriter writer;
    private String filePath;
    private ObjectMapper objectMapper;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
        } catch (IOException e) {
            logger.error("Failed to open a file for writing.", e);
        }
    }

    @Override
    public void execute(Tuple tuple) {
        int sumOfOperations = tuple.getIntegerByField("sumOfOperations");
        long beginningTimestamp = tuple.getLongByField("beginningTimestamp");
        long endTimestamp = tuple.getLongByField("endTimestamp");

        if(sumOfOperations > 200) {
            AggregatedWindow aggregatedWindow = new AggregatedWindow(sumOfOperations, beginningTimestamp, endTimestamp);
            try {
                writer.write(objectMapper.writeValueAsString(aggregatedWindow));
                writer.write("\n");
                writer.flush();
            } catch (IOException e) {
                logger.error("Failed to write data to file.", e);
            }
        }
    }

    public FileWritingBolt(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void cleanup() {
        try {
            writer.close();
        } catch (IOException e) {
            logger.error("Failed to close the writer!");
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
