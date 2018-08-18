package com.baeldung;

import com.baeldung.flink.BackupAggregator;
import com.baeldung.flink.InputMessageTimestampAssigner;
import com.baeldung.flink.WordsCapitalizer;
import com.baeldung.model.Backup;
import com.baeldung.model.InputMessage;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

import static com.baeldung.flink.Consumers.*;
import static com.baeldung.flink.Producers.*;

public class FlinkDataPipeline {

    public static void capitalize() throws Exception {
        String inputTopic = "flink_input";
        String outputTopic = "flink_output";
        String consumerGroup = "baeldung";
        String address = "localhost:9092";

        StreamExecutionEnvironment environment =
          StreamExecutionEnvironment.getExecutionEnvironment();

        FlinkKafkaConsumer011<String> flinkKafkaConsumer =
          createStringConsumerForTopic(inputTopic, address, consumerGroup);
        flinkKafkaConsumer.setStartFromEarliest();

        DataStream<String> stringInputStream =
          environment.addSource(flinkKafkaConsumer);

        FlinkKafkaProducer011<String> flinkKafkaProducer =
        createStringProducer(outputTopic, address);

        stringInputStream
          .map(new WordsCapitalizer())
          .addSink(flinkKafkaProducer);

        environment.execute();
    }

public static void createBackup () throws Exception {
    String inputTopic = "flink_input";
    String outputTopic = "flink_output";
    String consumerGroup = "baeldung";
    String kafkaAddress = "192.168.99.100:9092";

    StreamExecutionEnvironment environment =
      StreamExecutionEnvironment.getExecutionEnvironment();

    environment.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

    FlinkKafkaConsumer011<InputMessage> flinkKafkaConsumer =
      createInputMessageConsumer(inputTopic, kafkaAddress, consumerGroup);
    flinkKafkaConsumer.setStartFromEarliest();

    flinkKafkaConsumer
      .assignTimestampsAndWatermarks(new InputMessageTimestampAssigner());
    FlinkKafkaProducer011<Backup> flinkKafkaProducer =
      createBackupProducer(outputTopic, kafkaAddress);

    DataStream<InputMessage> inputMessagesStream =
      environment.addSource(flinkKafkaConsumer);

    inputMessagesStream
      .timeWindowAll(Time.hours(24))
      .aggregate(new BackupAggregator())
      .addSink(flinkKafkaProducer);

    environment.execute();
}

    public static void main(String[] args) throws Exception {
        createBackup();
    }

}
