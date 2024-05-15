package com.baeldung.ksqldb;

import io.confluent.ksql.api.client.Client;
import io.confluent.ksql.api.client.ClientOptions;
import io.confluent.ksql.api.client.KsqlObject;
import io.confluent.ksql.api.client.QueryInfo;
import io.confluent.ksql.api.client.QueryInfo.QueryType;
import io.confluent.ksql.api.client.Row;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Awaitility.given;

@Testcontainers
class KsqlDBApplicationLiveTest {

    private static final File KSQLDB_COMPOSE_FILE = new File("src/test/resources/docker/docker-compose.yml");

    private static final Map<String, Object> PROPERTIES = Collections.singletonMap("auto.offset.reset", "earliest");

    private static final String KSQLDB_SERVER_HOST = "localhost";
    private static final int KSQLDB_SERVER_PORT = 8088;

    @Container
    public static DockerComposeContainer dockerComposeContainer =
      new DockerComposeContainer<>(KSQLDB_COMPOSE_FILE)
        .withServices("zookeeper", "broker", "ksqldb-server")
        .withExposedService("ksqldb-server", 8088,
          Wait.forHealthcheck().withStartupTimeout(Duration.ofMinutes(5)))
        .withLocalCompose(true);

    private KsqlDBApplication ksqlDBApplication;

    private Client client;

    @BeforeEach
    void setup() {
        ClientOptions options = ClientOptions.create()
          .setHost(KSQLDB_SERVER_HOST)
          .setPort(KSQLDB_SERVER_PORT);
        client = Client.create(options);

        ksqlDBApplication = new KsqlDBApplication(client);
    }

    @AfterEach
    void tearDown() {
        deleteAlerts();
    }

    @Test
    void givenSensorReadings_whenSubscribedToAlerts_thenAlertsAreConsumed() {
        createAlertsMaterializedView();
        RowSubscriber<Alert> alertSubscriber = new RowSubscriber<>(Alert.class);

        CompletableFuture<Void> result = ksqlDBApplication.subscribeOnAlerts(alertSubscriber);
        insertSampleData();

        assertThat(result).isNotNull();
        await().atMost(Duration.ofMinutes(3)).untilAsserted(() ->
          assertThat(alertSubscriber.consumedItems)
            .containsOnly(
              expectedAlert("sensor-1", "2021-08-01 09:30:00", "2021-08-01 10:00:00", 28.0),
              expectedAlert("sensor-2", "2021-08-01 10:00:00", "2021-08-01 10:30:00", 26.0)
            )
        );
    }

    @Test
    void givenSensorReadings_whenPullQueryForRow_thenRowIsReturned() {
        createAlertsMaterializedView();
        insertSampleData();

        String pullQuery = "SELECT * FROM alerts WHERE sensor_id = 'sensor-2';";

        given().ignoreExceptions()
          .await().atMost(Duration.ofMinutes(1))
          .untilAsserted(() -> {
              // it may be possible that the materialized view is not updated with sample data yet
              // so ignore TimeoutException and try again
              List<Row> rows = client.executeQuery(pullQuery, PROPERTIES)
                .get(10, TimeUnit.SECONDS);

              assertThat(rows).hasSize(1);

              Row row = rows.get(0);
              assertThat(row.getString("SENSOR_ID")).isEqualTo("sensor-2");
              assertThat(row.getString("START_PERIOD")).isEqualTo("2021-08-01 10:00:00");
              assertThat(row.getString("END_PERIOD")).isEqualTo("2021-08-01 10:30:00");
              assertThat(row.getDouble("AVERAGE_READING")).isEqualTo(26.0);
        });
    }

    private void createAlertsMaterializedView() {
        ksqlDBApplication.createReadingsStream().join();
        ksqlDBApplication.createAlertsTable().join();
    }

    private void insertSampleData() {
        ksqlDBApplication.insert(
          Arrays.asList(
            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 09:00:00").put("reading", 22),
            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 09:10:00").put("reading", 20),
            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 09:20:00").put("reading", 20),

            // these reading will exceed the alert threshold (sensor-1)
            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 09:30:00").put("reading", 24),
            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 09:40:00").put("reading", 30),
            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 09:50:00").put("reading", 30),

            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 10:00:00").put("reading", 24),

            // these reading will exceed the alert threshold (sensor-2)
            new KsqlObject().put("sensor_id", "sensor-2").put("timestamp", "2021-08-01 10:00:00").put("reading", 26),
            new KsqlObject().put("sensor_id", "sensor-2").put("timestamp", "2021-08-01 10:10:00").put("reading", 26),
            new KsqlObject().put("sensor_id", "sensor-2").put("timestamp", "2021-08-01 10:20:00").put("reading", 26),

            new KsqlObject().put("sensor_id", "sensor-1").put("timestamp", "2021-08-01 10:30:00").put("reading", 24)
          )
        ).join();
    }

    private void deleteAlerts() {
        client.listQueries()
          .thenApply(queryInfos -> queryInfos.stream()
            .filter(queryInfo -> queryInfo.getQueryType() == QueryType.PERSISTENT)
            .map(QueryInfo::getId)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Persistent query not found")))
          .thenCompose(id -> client.executeStatement("TERMINATE " + id + ";"))
          .thenCompose(result -> client.executeStatement("DROP TABLE alerts DELETE TOPIC;"))
          .thenCompose(result -> client.executeStatement("DROP STREAM readings DELETE TOPIC;"))
          .join();
    }

    private Alert expectedAlert(String sensorId, String startPeriod, String endPeriod, double average) {
        return Alert.builder()
          .sensorId(sensorId)
          .startPeriod(startPeriod)
          .endPeriod(endPeriod)
          .averageReading(average)
          .build();
    }
}
