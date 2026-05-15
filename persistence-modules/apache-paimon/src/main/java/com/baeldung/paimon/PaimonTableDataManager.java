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
import org.apache.paimon.predicate.Predicate;
import org.apache.paimon.predicate.PredicateBuilder;
import org.apache.paimon.reader.RecordReader;
import org.apache.paimon.table.Table;
import org.apache.paimon.table.sink.BatchTableCommit;
import org.apache.paimon.table.sink.BatchTableWrite;
import org.apache.paimon.table.sink.BatchWriteBuilder;
import org.apache.paimon.table.sink.CommitMessage;
import org.apache.paimon.table.source.ReadBuilder;
import org.apache.paimon.table.source.Split;
import org.apache.paimon.table.source.TableRead;
import org.apache.paimon.types.RowType;
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
                    timestamp,
                    BinaryString.fromString(metric.getCreatedBy())                    
                ), 2);
            } catch (Exception e) {
                logger.error("Error writing metric", e);
            }
        });

        List<CommitMessage> messages = write.prepareCommit();

        BatchTableCommit commit = builder.newCommit();
        commit.commit(messages);
    }

    private static Timestamp convertToTimestamp(String createTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(createTime, formatter);
        return Timestamp.fromLocalDateTime(localDateTime);
    }


    public static List<Metric> fetchMetricsBySourceAndDateRange(Catalog catalog, Identifier tableId,
            String source, String startDate, String endDate) throws Exception {

        Table table = catalog.getTable(tableId);
        RowType rowType = table.rowType();
        PredicateBuilder predicateBuilder = new PredicateBuilder(rowType);

       int[] projection = new int[] {0, 1, 2, 3, 4};

        Predicate sourcePredicate = predicateBuilder.equal(3, BinaryString.fromString(source));
        Predicate dateRangePredicate = predicateBuilder.between(4, convertToTimestamp(startDate), convertToTimestamp(endDate));
        Predicate predicate = PredicateBuilder.and(sourcePredicate, dateRangePredicate);

        ReadBuilder readBuilder = table.newReadBuilder().withFilter(predicate).withProjection(projection);

        List<Split> splits = readBuilder.newScan().plan().splits();

        TableRead read = readBuilder.newRead().executeFilter();

        RecordReader<InternalRow> reader = read.createReader(splits);

        List<Metric> results = new ArrayList<>();

        reader.forEachRemaining(internalRow -> {
            String deviceId = internalRow.getString(0).toString();
            String metricsName = internalRow.getString(1).toString();
            double metricsValue = internalRow.getDouble(2);
            String sourceValue = internalRow.getString(3).toString();
            Timestamp timestamp = internalRow.getTimestamp(4, 3);
            String createTime = ConvertTimestampToStr(timestamp); 

            Metric metric = new Metric(deviceId, metricsName, metricsValue, sourceValue, createTime, null);
            logger.info("Fetched Metric: {}", metric);
            results.add(metric);
        });

        return results;
    }

    private static String ConvertTimestampToStr(Timestamp timestamp) {
        return timestamp.toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}