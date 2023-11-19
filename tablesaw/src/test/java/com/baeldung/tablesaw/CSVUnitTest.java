package com.baeldung.tablesaw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.api.TimeColumn;
import tech.tablesaw.io.csv.CsvReadOptions;
import tech.tablesaw.selection.Selection;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static tech.tablesaw.aggregate.AggregateFunctions.max;
import static tech.tablesaw.aggregate.AggregateFunctions.mean;
import static tech.tablesaw.aggregate.AggregateFunctions.min;
import static tech.tablesaw.aggregate.AggregateFunctions.stdDev;

class CSVUnitTest {

    private static Table table;

    @BeforeEach
    void setup() {
        URL resource = CSVUnitTest.class.getClassLoader().getResource("avocado.csv");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Path filePath = Paths.get(resource.getPath());
        File file = filePath.toFile();
        CsvReadOptions csvReadOptions =
                CsvReadOptions.builder(file)
                        .separator(',')
                        .header(true)
                        .dateFormat(formatter)
                        .build();
        table = Table.read().usingOptions(csvReadOptions);
    }

    @Test
    void shouldReturnTheShapeStringOnAvocadoDataSet() {
        assertThat(table.shape()).isEqualTo("avocado.csv: 18249 rows X 14 cols");
    }

    @Test
    void shouldOrderTheTableByDateInAscendingOrder() {
        Table ascendingDateSortedTable = table.sortAscendingOn("Date");
        assertThat(ascendingDateSortedTable.dateColumn("Date").get(0)).isEqualTo(LocalDate.parse("2015-01-04"));
    }

    @Test
    void shouldOrderTheTableByDateInDescendingOrder() {
        Table descendingDateSortedTable = table.sortDescendingOn("Date");
        assertThat(descendingDateSortedTable.dateColumn("Date").get(0)).isEqualTo(LocalDate.parse("2018-03-25"));
    }

    @Test
    void shouldOrderTheTableByYearAndAveragePriceInAscendingOrder() {
        Table ascendingYearAndAveragePriceSortedTable = table.sortOn("year", "-AveragePrice");
        assertThat(ascendingYearAndAveragePriceSortedTable.intColumn("year").get(0)).isEqualTo(2015);
        assertThat(ascendingYearAndAveragePriceSortedTable.numberColumn("AveragePrice").get(0)).isEqualTo(2.79);
    }

    @Test
    void shouldRemoveTheValueWhenSettingAsMissing() {
        DoubleColumn averagePrice = table.doubleColumn("AveragePrice").setMissing(0);
        assertThat(averagePrice.get(0)).isNull();
    }

    @Test
    void shouldAppendDataToTheColumn() {
        DoubleColumn averagePrice = table.doubleColumn("AveragePrice");
        averagePrice.append(1.123);
        assertThat(averagePrice.get(averagePrice.size() - 1)).isEqualTo(1.123);
    }

    @Test
    void shouldAppendDataToAveragePriceColumn() {
        DoubleColumn averagePrice2 = table.doubleColumn("AveragePrice").copy();
        averagePrice2.setName("AveragePrice2");
        averagePrice2.append(1.123);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> table.addColumns(averagePrice2));
    }

    @Test
    void shouldReturnTheTableContainingDataFrom2017andAveragePriceIsGreaterThan2Only() {
        DateColumn dateTable = table.dateColumn("Date");
        DoubleColumn averagePrice = table.doubleColumn("AveragePrice");
        Selection selection = dateTable.isInYear(2017).and(averagePrice.isGreaterThan(2D));
        Table table2017 = table.where(selection);
        assertThat(table2017.intColumn("year")).containsOnly(2017);
        assertThat(table2017.doubleColumn("AveragePrice")).allMatch(avrgPrice -> avrgPrice > 2D);
    }

    @Test
    void shouldPrintToStandardOutputStatisticsOfAveragePriceByYearData() {
        Table summary = table.summarize("AveragePrice", max, min, mean, stdDev).by("year");
        System.out.println(summary.print());
        Assertions.assertTrue(true);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenCreatingTableWithDifferentSizeColumns() {
        StringColumn type = StringColumn.create("type");
        StringColumn region = StringColumn.create("region");
        type.addAll(List.of("Country", "City"));
        region.append("USA");
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> Table.create(type, region));
    }

    @Test
    void shouldNotThrowIllegalArgumentExceptionWhenCreatingTableWithDifferentSameSizeColumns() {
        StringColumn type = StringColumn.create("type");
        StringColumn region = StringColumn.create("region");
        type.addAll(List.of("Country", "City"));
        region.append("USA");
        region.appendMissing();
        assertThatNoException().isThrownBy(() -> Table.create(type, region));
    }

    @Test
    void shouldAddColumnToTable() {
        TimeColumn time = TimeColumn.create("time");
        Table table = Table.create("test");
        table.addColumns(time);
        assertThat(table.columnNames()).contains("time");
    }

    @Test
    void shouldBeEqualTwoValuesFromDifferentRowsOnTheTypeColumn() {
        StringColumn type = table.stringColumn("type");
        List<String> conventional = type.where(type.isEqualTo("conventional")).asList().stream()
                .limit(2)
                .toList();
        assertThat(conventional.get(0)).isSameAs(conventional.get(1));
    }
}
