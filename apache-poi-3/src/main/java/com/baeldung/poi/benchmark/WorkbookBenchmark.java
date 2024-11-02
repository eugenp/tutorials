package com.baeldung.poi.benchmark;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.profile.MemPoolProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 1, time = 2)
@Measurement(iterations = 3, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(value = 1)
public class WorkbookBenchmark
{

    @Benchmark
    public static void write2500RowsToHSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new HSSFWorkbook(), 2500, blackhole);
    }

    @Benchmark
    public static void write5000RowsToHSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new HSSFWorkbook(), 5000, blackhole);
    }

    @Benchmark
    public static void write10000RowsToHSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new HSSFWorkbook(), 10000, blackhole);
    }

    @Benchmark
    public static void write20000RowsToHSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new HSSFWorkbook(), 20000, blackhole);
    }

    @Benchmark
    public static void write40000RowsToHSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new HSSFWorkbook(), 40000, blackhole);
    }

    @Benchmark
    public static void write2500RowsToXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new XSSFWorkbook(), 2500, blackhole);
    }

    @Benchmark
    public static void write5000RowsToXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new XSSFWorkbook(), 5000, blackhole);
    }

    @Benchmark
    public static void write10000RowsToXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new XSSFWorkbook(), 10000, blackhole);
    }

    @Benchmark
    public static void write20000RowsToXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new XSSFWorkbook(), 20000, blackhole);
    }

    @Benchmark
    public static void write40000RowsToXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new XSSFWorkbook(), 40000, blackhole);
    }

    @Benchmark
    public static void write2500RowsToSXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new SXSSFWorkbook(), 2500, blackhole);
    }

    @Benchmark
    public static void write5000RowsToSXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(new SXSSFWorkbook(), 5000, blackhole);
    }

    @Benchmark
    public static void write10000RowsToSXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(getSXSSFWorkbook(), 10000, blackhole);
    }

    @Benchmark
    public static void write20000RowsToSXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(getSXSSFWorkbook(), 20000, blackhole);
    }

    @Benchmark
    public static void write40000RowsToSXSSFWorkbook(Blackhole blackhole) throws IOException {
        writeRowsToWorkbook(getSXSSFWorkbook(), 40000, blackhole);
    }

    private static SXSSFWorkbook getSXSSFWorkbook() {
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        workbook.setCompressTempFiles(true);
        return workbook;
    }

    public static void writeRowsToWorkbook(Workbook workbook, int iterations, Blackhole blackhole) throws IOException {
        Sheet sheet = workbook.createSheet();
        for (int n=0;n<iterations;n++) {
            Row row = sheet.createRow(sheet.getLastRowNum()+1);
            for (int c=0;c<256;c++) {
                Cell cell = row.createCell(c);
                cell.setCellValue("abcdefghijklmnopqrstuvwxyz");
            }
        }
        workbook.close();
        blackhole.consume(workbook);
    }

    public static void main(String[] args) throws Exception {
        Options options = new OptionsBuilder()
                .include(WorkbookBenchmark.class.getSimpleName()).threads(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .addProfiler(MemPoolProfiler.class)
                .jvmArgs("-server").build();
        new Runner(options).run();
    }

}