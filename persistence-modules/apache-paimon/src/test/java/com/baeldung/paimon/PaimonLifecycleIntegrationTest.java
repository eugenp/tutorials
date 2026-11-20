package com.baeldung.paimon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Path;
import java.util.List;
import org.apache.paimon.catalog.Catalog;
import org.apache.paimon.catalog.Identifier;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaimonLifecycleIntegrationTest {
    private static final Logger logger =
        LoggerFactory.getLogger(PaimonLifecycleIntegrationTest.class);

    private Catalog catalog;
    private Identifier tableId;

    private String WAREHOUSE_PATH;

    @BeforeAll
    void setup(@TempDir Path tempDir) {
        WAREHOUSE_PATH = getWarehousePath(tempDir);
        logger.info("Warehouse path set to: {}", WAREHOUSE_PATH);
    }

    @AfterAll
    void cleanup() {
        if (catalog != null) {
            try {
                if (catalog.listTables("metric_db").contains("metrics")) {
                    catalog.dropTable(tableId, true);
                }
                if (catalog.listDatabases().contains("metric_db")) {
                    catalog.dropDatabase("metric_db", true, true);
                }
                logger.info("Cleanup completed successfully");
            } catch (Exception e) {
                logger.error("Error during cleanup", e);
            }
        }
    }

    @Test
    @Order(1)
    void whenCallCreateTable_thenTableCreated() throws Exception {
        logger.info("Warehouse path set to: {}", WAREHOUSE_PATH);
        catalog = PaimonDatabaseManager.createCatalog(WAREHOUSE_PATH);
        assertNotNull(catalog);

        tableId = PaimonDatabaseManager.createTable(catalog);
        assertTrue(catalog.listTables("metric_db").contains("metrics"));
        assertNotNull(tableId);
    }

    @Test
    @Order(2)
    void whenCallInsertRecords_thenRecordsInserted() throws Exception {
        assertTrue(catalog.listTables("metric_db").contains("metrics"));
        PaimonTableDataManager.insert(catalog, tableId, getMetrics());
    }

    @Test
    @Order(3)
    void whenCallReadRecords_thenRecordsRead() throws Exception {
        List<Metric> metrics = PaimonTableDataManager.fetchMetricsBySourceAndDateRange(catalog,
            tableId, "collector", "2026-04-21 18:58:19", "2026-04-21 18:58:30");
        assertFalse(metrics.isEmpty());
        assertTrue(metrics.size() == 4);
    }

    @Test     
    @Order(4)     
    void whenCallDeleteRecord_thenRecordDeleted() throws Exception {
        Metric metric = PaimonTableDataManager.fetchMetricByDeviceIdMetricNameAndCreatedDate(catalog, tableId, "dev_136", "disk_io", "2026-04-21 18:58:26");
        assertNotNull(metric);

        PaimonTableDataManager.deleteRecordsByDeviceIdMetricNameAndCreatedDate(
            catalog, tableId, "dev_136", 
            "disk_io", "2026-04-21 18:58:26"
        );

        metric = PaimonTableDataManager.fetchMetricByDeviceIdMetricNameAndCreatedDate(
            catalog, tableId, "dev_136",
            "disk_io", "2026-04-21 18:58:26"
        );
        assertTrue(metric == null);
    }

    @Test
    @Order(5)
    void whenCallUpdateRecord_thenRecordUpdated() throws Exception {
        Metric metric = PaimonTableDataManager.fetchMetricByDeviceIdMetricNameAndCreatedDate(
            catalog, tableId, 
            "dev_137", "cpu_usage",
            "2026-04-21 18:58:27"
        );
        assertNotNull(metric);

        assertEquals("active", metric.getState());

        PaimonTableDataManager.updateMetricStateByDeviceIdMetricNameAndCreatedDate(
            catalog, tableId, "dev_137",
             "cpu_usage", "inactive", 
             "2026-04-21 18:58:27"
        );

        metric = PaimonTableDataManager.fetchMetricByDeviceIdMetricNameAndCreatedDate(
            catalog, tableId, "dev_137",
            "cpu_usage", "2026-04-21 18:58:27"
        );
        assertNotNull(metric);
        assertEquals("inactive", metric.getState());
    }
 
    private List<Metric> getMetrics() {
        return new MetricReader().readMetrics();
    }

    private String getWarehousePath(Path tempDir) {
        return "file:" + tempDir.toString();
    }
}
