package com.baeldung.paimon;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.apache.paimon.catalog.Catalog;
import org.apache.paimon.catalog.Identifier;
import org.apache.paimon.data.BinaryString;
import org.apache.paimon.data.GenericRow;
import org.apache.paimon.data.InternalRow;
import org.apache.paimon.data.Timestamp;
import org.apache.paimon.reader.RecordReader;
import org.apache.paimon.table.Table;
import org.apache.paimon.table.sink.BatchTableCommit;
import org.apache.paimon.table.sink.BatchTableWrite;
import org.apache.paimon.table.sink.BatchWriteBuilder;
import org.apache.paimon.table.sink.CommitMessage;
import org.apache.paimon.table.source.ReadBuilder;
import org.apache.paimon.table.source.Split;
import org.apache.paimon.table.source.TableRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaimonTableDataManager {

    final static Logger logger = LoggerFactory.getLogger(PaimonTableDataManager.class);

    public static void insert(Catalog catalog, Identifier tableId, List<Metric> metrics) throws Exception {

        Table table = catalog.getTable(tableId);

        BatchWriteBuilder builder = table.newBatchWriteBuilder();

        BatchTableWrite write = builder.newWrite();

        metrics.forEach(metric -> {
            try {

                Timestamp timestamp = convertToTimestamp(metric.getCreateTime());
                //device_id,metric_name,create_time,metric_value,source
                write.write(GenericRow.of(
                    BinaryString.fromString(metric.getDeviceId()),
                    BinaryString.fromString(metric.getMetricsName()),
                    metric.getMetricsValue(),
                    BinaryString.fromString(metric.getSource()),
                    timestamp                    
                ), 0);
            } catch (Exception e) {
                logger.error("Error writing metric", e);
            }
        });

        List<CommitMessage> messages = write.prepareCommit();

        BatchTableCommit commit = builder.newCommit();
        commit.commit(messages);
    }

    public static List<String> read(Catalog catalog, Identifier tableId) throws Exception {

        Table table = catalog.getTable(tableId);

        ReadBuilder readBuilder = table.newReadBuilder();

        List<Split> splits = readBuilder.newScan().plan().splits();

        TableRead read = readBuilder.newRead();

        RecordReader<InternalRow> reader = read.createReader(splits);

        List<String> results = new ArrayList<>();

        reader.forEachRemaining(row -> {
            results.add(row.toString());
        });

        return results;
    }

    private static Timestamp convertToTimestamp(String createTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(createTime, formatter);
        return Timestamp.fromLocalDateTime(localDateTime);
    }
}