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
import org.apache.paimon.types.RowKind;
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
                GenericRow row = createGenericRow(metric);
                write.write(row, 0);
            } catch (Exception e) {
                logger.error("Error writing metric", e);
            }
        });

        List<CommitMessage> messages = write.prepareCommit();
        BatchTableCommit commit = builder.newCommit();
        commit.commit(messages);
    }

    public static void updateMetricStateByDeviceIdMetricNameAndCreatedDate(Catalog catalog,
        Identifier tableId, String deviceId, String metricName, String newState, String createdDate)
        throws Exception {

        Metric metric = fetchMetricByDeviceIdMetricNameAndCreatedDate(catalog, tableId, deviceId, metricName, createdDate);
        Table table = catalog.getTable(tableId);

        BatchWriteBuilder builder = table.newBatchWriteBuilder();

        BatchTableWrite write = builder.newWrite();
        metric.setState(newState);
        write.write(createGenericRow(metric), 0);

        List<CommitMessage> messages = write.prepareCommit();

        BatchTableCommit commit = builder.newCommit();
        commit.commit(messages);
    }

    public static void deleteRecordsByDeviceIdMetricNameAndCreatedDate(Catalog catalog, Identifier tableId,
        String deviceId, String metricsName, String createdDate) throws Exception {
        Metric metric = PaimonTableDataManager.fetchMetricByDeviceIdMetricNameAndCreatedDate(catalog, tableId, deviceId, metricsName, createdDate);
        Table table = catalog.getTable(tableId);

        BatchWriteBuilder builder = table.newBatchWriteBuilder();

        BatchTableWrite write = builder.newWrite();

        GenericRow deleteRow = createGenericRow(metric);

        deleteRow.setRowKind(RowKind.DELETE);

        write.write(deleteRow, 0);

        List<CommitMessage> messages = write.prepareCommit();

        builder.newCommit().commit(messages);
    }

    public static List<Metric> fetchMetricsBySourceAndDateRange(Catalog catalog, Identifier tableId,
            String source, String startDate, String endDate) throws Exception {

        Table table = catalog.getTable(tableId);
        RowType rowType = table.rowType();
        PredicateBuilder predicateBuilder = new PredicateBuilder(rowType);

       int[] projection = new int[] {0, 1, 2, 3, 4, 6};

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
            String state = internalRow.getString(5).toString();

            Metric metric = new Metric(deviceId, metricsName, metricsValue, sourceValue, createTime, null, state);
            logger.info("Fetched Metric: {}", metric);
            results.add(metric);
        });

        return results;
    }

    public static Metric fetchMetricByDeviceIdMetricNameAndCreatedDate(Catalog catalog, Identifier tableId,
        String deviceId, String metricsName, String createdDate) throws Exception {
        Table table = catalog.getTable(tableId);
        RowType rowType = table.rowType();
        PredicateBuilder predicateBuilder = new PredicateBuilder(rowType);

        int[] projection = new int[] {0, 1, 2, 3, 4, 5, 6};

        Predicate devicePredicate = predicateBuilder.equal(0, BinaryString.fromString(deviceId));
        Predicate metricPredicate = predicateBuilder.equal(1, BinaryString.fromString(metricsName));
        Predicate datePredicate = predicateBuilder.equal(4, convertToTimestamp(createdDate));
        Predicate predicate = PredicateBuilder.and(devicePredicate, metricPredicate, datePredicate);

        ReadBuilder readBuilder = table.newReadBuilder().withFilter(predicate).withProjection(projection);

        List<Split> splits = readBuilder.newScan().plan().splits();

        TableRead read = readBuilder.newRead().executeFilter();

        RecordReader<InternalRow> reader = read.createReader(splits);
        List<Metric> results = new ArrayList<>();
        
        reader.forEachRemaining( internalRow -> {
            String deviceIdValue = internalRow.getString(0).toString();
            String metricsNameValue = internalRow.getString(1).toString();
            double metricsValue = internalRow.getDouble(2);
            String sourceValue = internalRow.getString(3).toString();
            Timestamp timestamp = internalRow.getTimestamp(4, 3);
            String createTime = ConvertTimestampToStr(timestamp); 
            String createdBy = internalRow.getString(5).toString();
            String state = internalRow.getString(6).toString();
            Metric metric = new Metric(deviceIdValue, metricsNameValue, metricsValue, sourceValue, createTime, createdBy, state);
            results.add(metric);
            logger.info("Fetched Metric: {}", metric);
         });

        return results.isEmpty() ? null : results.get(0);
    }

    private static String ConvertTimestampToStr(Timestamp timestamp) {
        return timestamp.toLocalDateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    private static Timestamp convertToTimestamp(String createTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.parse(createTime, formatter);
        return Timestamp.fromLocalDateTime(localDateTime);
    }

    private static GenericRow createGenericRow(Metric metric) {
        GenericRow row = GenericRow.of(
            BinaryString.fromString(metric.getDeviceId()),
            BinaryString.fromString(metric.getMetricsName()),
            metric.getMetricsValue(),
            BinaryString.fromString(metric.getSource()),
            convertToTimestamp(metric.getCreateTime()),
            BinaryString.fromString(metric.getCreatedBy()),
            BinaryString.fromString(metric.getState())
        );
        return row;
    }
}