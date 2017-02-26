package org.baeldung.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Sample {
    private final static byte[] cellData = Bytes.toBytes("cell_data");

    /**
     * Drop tables if this value is set true.
     */
    static boolean INITIALIZE_AT_FIRST = true;

    /**
     * <table>
     * <tr>
     * <tb>Row1</tb> <tb>Family1:Qualifier1</tb> <tb>Family1:Qualifier2</tb>
     * </tr>
     * <tr>
     * <tb>Row2</tb> <tb>Family1:Qualifier1</tb> <tb>Family2:Qualifier3</tb>
     * </tr>
     * <tr>
     * <tb>Row3</tb> <tb>Family1:Qualifier1</tb> <tb>Family2:Qualifier3</tb>
     * </tr>
     * </table>
     */
    private final TableName table1 = TableName.valueOf("Table1");
    private final String family1 = "Family1";
    private final String family2 = "Family2";

    private final byte[] row1 = Bytes.toBytes("Row1");
    private final byte[] row2 = Bytes.toBytes("Row2");
    private final byte[] row3 = Bytes.toBytes("Row3");
    private final byte[] qualifier1 = Bytes.toBytes("Qualifier1");
    private final byte[] qualifier2 = Bytes.toBytes("Qualifier2");
    private final byte[] qualifier3 = Bytes.toBytes("Qualifier3");

    private void createTable(HBaseAdmin admin) throws IOException {
        HTableDescriptor desc = new HTableDescriptor(table1);
        desc.addFamily(new HColumnDescriptor(family1));
        desc.addFamily(new HColumnDescriptor(family2));
        admin.createTable(desc);
    }

    private void delete(HBaseAdmin admin, HTableInterface table) throws IOException {
        System.out.println("\n*** DELETE ~Insert data and then delete it~ ***");

        System.out.println("Inserting a data to be deleted later.");
        Put put = new Put(row1);
        put.addColumn(family1.getBytes(), qualifier1, cellData);
        table.put(put);

        Get get = new Get(row1);
        Result result = table.get(get);
        byte[] value = result.getValue(family1.getBytes(), qualifier1);
        System.out.println("Fetch the data: " + Bytes.toString(value));
        assert Arrays.equals(cellData, value);

        System.out.println("Deleting");
        Delete delete = new Delete(row1);
        delete.addColumn(family1.getBytes(), qualifier1);
        table.delete(delete);

        result = table.get(get);
        value = result.getValue(family1.getBytes(), qualifier1);
        System.out.println("Fetch the data: " + Bytes.toString(value));
        assert Arrays.equals(cellData, value);

        System.out.println("Done. ");
    }

    private void deleteTable(HBaseAdmin admin) throws IOException {
        if (admin.tableExists(table1)) {
            admin.disableTable(table1);
            admin.deleteTable(table1);
        }
    }

    private void filters(HBaseAdmin admin, HTableInterface table) throws IOException {
        System.out.println("\n*** FILTERS ~ scanning with filters to fetch a row of which key is larget than \"Row1\"~ ***");
        Filter filter1 = new PrefixFilter(row2);
        Filter filter2 = new QualifierFilter(CompareOp.GREATER_OR_EQUAL, new BinaryComparator(
                qualifier1));

        List<Filter> filters = Arrays.asList(filter1, filter2);
        Filter filter3 = new FilterList(Operator.MUST_PASS_ALL, filters);

        Scan scan = new Scan();
        scan.setFilter(filter3);

        ResultScanner scanner = table.getScanner(scan);
        try {
            int i = 0;
            for (Result result : scanner) {
                System.out.println("Filter " + scan.getFilter() + " matched row: " + result);
                i++;
            }
            assert i == 1 : "This filtering sample should return 1 row but was " + i + ".";
        } finally {
            scanner.close();
        }
        System.out.println("Done. ");
    }

    private void get(HBaseAdmin admin, HTableInterface table) throws IOException {
        System.out.println("\n*** GET example ~fetching the data in Family1:Qualifier1~ ***");

        Get g = new Get(row1);
        Result r = table.get(g);
        byte[] value = r.getValue(family1.getBytes(), qualifier1);

        System.out.println("Fetched value: " + Bytes.toString(value));
        assert Arrays.equals(cellData, value);
        System.out.println("Done. ");
    }

    private void put(HBaseAdmin admin, HTableInterface table) throws IOException {
        System.out.println("\n*** PUT example ~inserting \"cell-data\" into Family1:Qualifier1 of Table1 ~ ***");

        // Row1 => Family1:Qualifier1, Family1:Qualifier2
        Put p = new Put(row1);
        p.addImmutable(family1.getBytes(), qualifier1, cellData);
        p.addImmutable(family1.getBytes(), qualifier2, cellData);
        table.put(p);

        // Row2 => Family1:Qualifier1, Family2:Qualifier3
        p = new Put(row2);
        p.addImmutable(family1.getBytes(), qualifier1, cellData);
        p.addImmutable(family2.getBytes(), qualifier3, cellData);
        table.put(p);

        // Row3 => Family1:Qualifier1, Family2:Qualifier3
        p = new Put(row3);
        p.addImmutable(family1.getBytes(), qualifier1, cellData);
        p.addImmutable(family2.getBytes(), qualifier3, cellData);
        table.put(p);

        admin.disableTable(table1);
        try {
            HColumnDescriptor desc = new HColumnDescriptor(row1);
            admin.addColumn(table1, desc);
            System.out.println("Success.");
        } catch (Exception e) {
            System.out.println("Failed.");
            System.out.println(e.getMessage());
        } finally {
            admin.enableTable(table1);
        }
        System.out.println("Done. ");
    }

    public void run(Configuration config) throws IOException {
        HBaseAdmin admin = new HBaseAdmin(config);
        HTableFactory factory = new HTableFactory();

        if (INITIALIZE_AT_FIRST) {
            deleteTable(admin);
        }

        if (!admin.tableExists(table1)) {
            createTable(admin);
        }

        HTableInterface table = factory.createHTableInterface(config, table1.getName());
        put(admin, table);
        get(admin, table);
        scan(admin, table);
        filters(admin, table);
        delete(admin, table);
        factory.releaseHTableInterface(table); // Disconnect
    }

    private void scan(HBaseAdmin admin, HTableInterface table) throws IOException {
        System.out.println("\n*** SCAN example ~fetching data in Family1:Qualifier1 ~ ***");

        Scan scan = new Scan();
        scan.addColumn(family1.getBytes(), qualifier1);

        ResultScanner scanner = table.getScanner(scan);
        try {
            for (Result result : scanner)
                System.out.println("Found row: " + result);
        } finally {
            scanner.close();
        }
        System.out.println("Done.");
    }
}