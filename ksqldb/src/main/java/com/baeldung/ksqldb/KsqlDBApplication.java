package com.baeldung.ksqldb;

import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ExecuteStatementResult;
import io.confluent.ksql.api.client.KsqlObject;
import io.confluent.ksql.api.client.Row;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Slf4j
public class KsqlDBApplication {

    private static final String CREATE_READINGS_STREAM = ""
      + "  CREATE STREAM readings (sensor_id VARCHAR KEY, timestamp VARCHAR, reading INT)"
      + "  WITH (KAFKA_TOPIC = 'readings',"
      + "        VALUE_FORMAT = 'JSON',"
      + "        TIMESTAMP = 'timestamp',"
      + "        TIMESTAMP_FORMAT = 'yyyy-MM-dd HH:mm:ss',"
      + "        PARTITIONS = 1);";

    private static final String CREATE_ALERTS_TABLE = ""
      + "  CREATE TABLE alerts AS"
      + "    SELECT"
      + "      sensor_id,"
      + "      TIMESTAMPTOSTRING(WINDOWSTART, 'yyyy-MM-dd HH:mm:ss', 'UTC') AS start_period,"
      + "      TIMESTAMPTOSTRING(WINDOWEND, 'yyyy-MM-dd HH:mm:ss', 'UTC') AS end_period,"
      + "      AVG(reading) AS average_reading"
      + "  FROM readings"
      + "  WINDOW TUMBLING (SIZE 30 MINUTES)"
      + "  GROUP BY sensor_id"
      + "  HAVING AVG(reading) > 25"
      + "  EMIT CHANGES;";

    private static final String ALERTS_QUERY = "SELECT * FROM alerts EMIT CHANGES;";

    private static final String READINGS_STREAM = "readings";

    private static final Map<String, Object> PROPERTIES = Collections.singletonMap("auto.offset.reset", "earliest");

    private final Client client;

    public CompletableFuture<ExecuteStatementResult> createReadingsStream() {
        return client.executeStatement(CREATE_READINGS_STREAM, PROPERTIES);
    }

    public CompletableFuture<ExecuteStatementResult> createAlertsTable() {
        return client.executeStatement(CREATE_ALERTS_TABLE, PROPERTIES);
    }

    public CompletableFuture<Void> insert(Collection<KsqlObject> rows) {
        return CompletableFuture.allOf(
          rows.stream()
            .map(row -> client.insertInto(READINGS_STREAM, row))
            .toArray(CompletableFuture[]::new)
        );
    }

    public CompletableFuture<Void> subscribeOnAlerts(Subscriber<Row> subscriber) {
        return client.streamQuery(ALERTS_QUERY, PROPERTIES)
          .thenAccept(streamedQueryResult -> streamedQueryResult.subscribe(subscriber))
          .whenComplete((result, ex) -> {
              if (ex != null) {
                  log.error("Alerts push query failed", ex);
              }
          });
    }

}
