package com.baeldung.jsonoptimization;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.zip.GZIPOutputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

class JsonOptimizationUnitTest {
    private static final String TEST_LABEL_DEFAULT_JSON = "Default JSON";
    private static final String TEST_LABEL_DEFAULT_JSON_NO_NULL = "Default JSON without null";
    private static final String TEST_LABEL_SHORT_NAMES = "Shorter attribute names";
    private static final String TEST_LABEL_SHORT_NAMES_NO_NULL = "Shorter attribute names without null";
    private static final String TEST_LABEL_CUSTOM_SERIALIZER = "Custom serializer";
    private static final String TEST_LABEL_SLIM_CUSTOM_SERIALIZER = "Slim custom serializer";
    private static final String TEST_LABEL_SLIM_CUSTOMER = "Slim customer";
    private static final String TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES = "Slim customer with shorter attribute names";
    private static DecimalFormat LENGTH_FORMATTER = new DecimalFormat("###,###,###");
    private static Customer[] customers;
    private ObjectMapper mapper;

    @BeforeAll
    static void setUpOnce() throws Exception {
        customers = Customer.fromMockFile();
    }

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void testSetUp() {
        assertEquals(1000, customers.length, "There should be a 1000 customers");
    }

    @Test
    void testDefaultJson() throws IOException {
        printBanner(TEST_LABEL_DEFAULT_JSON);
        byte[] plainJson = createPlainJson(TEST_LABEL_DEFAULT_JSON, customers);
        compressJson(TEST_LABEL_DEFAULT_JSON, plainJson);
    }

    @Test
    void testDefaultNoNull() throws IOException {
        printBanner(TEST_LABEL_DEFAULT_JSON_NO_NULL);
        CustomerNoNull[] defaultNoNull = CustomerNoNull.fromCustomers(customers);
        byte[] plainJson = createPlainJson(TEST_LABEL_DEFAULT_JSON_NO_NULL, defaultNoNull);
        compressJson(TEST_LABEL_DEFAULT_JSON_NO_NULL, plainJson);
    }

    @Test
    void testShortNames() throws IOException {
        printBanner(TEST_LABEL_SHORT_NAMES);
        CustomerShortNames[] shorterOnes = CustomerShortNames.fromCustomers(customers);
        byte[] shorterJson = createPlainJson(TEST_LABEL_SHORT_NAMES, shorterOnes);
        compressJson(TEST_LABEL_SHORT_NAMES, shorterJson);
    }

    @Test
    void testShortNamesNoNull() throws IOException {
        printBanner(TEST_LABEL_SHORT_NAMES_NO_NULL);
        CustomerShortNamesNoNull[] shorterOnesNoNull = CustomerShortNamesNoNull.fromCustomers(customers);
        byte[] shorterJson = createPlainJson(TEST_LABEL_SHORT_NAMES_NO_NULL, shorterOnesNoNull);
        compressJson(TEST_LABEL_SHORT_NAMES_NO_NULL, shorterJson);
    }

    @Test
    void testSlim() throws IOException {
        printBanner(TEST_LABEL_SLIM_CUSTOMER);
        CustomerSlim[] slimOnes = CustomerSlim.fromCustomers(customers);
        byte[] slimJson = createPlainJson(TEST_LABEL_SLIM_CUSTOMER, slimOnes);
        compressJson(TEST_LABEL_SLIM_CUSTOMER, slimJson);
    }

    @Test
    void testSlimShortNames() throws IOException {
        printBanner(TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES);
        CustomerSlimShortNames[] slimOnes = CustomerSlimShortNames.fromCustomers(customers);
        byte[] slimJson = createPlainJson(TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES, slimOnes);
        compressJson(TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES, slimJson);
    }

    @Test
    void testCustomSerializer() throws IOException {
        printBanner(TEST_LABEL_CUSTOM_SERIALIZER);
        
        SimpleModule serializer = new SimpleModule("CustomDeSerializer", new Version(1, 0, 0, null, null, null));
        serializer.addSerializer(Customer.class, new CustomerSerializer());
        serializer.addDeserializer(Customer.class, new CustomerDeserializer());
        mapper.registerModule(serializer);
        
        byte[] plainJson = createPlainJson(TEST_LABEL_CUSTOM_SERIALIZER, customers);
        compressJson(TEST_LABEL_CUSTOM_SERIALIZER, plainJson);
    }
    
    @Test
    void testSlimCustomSerializer() throws IOException {
        printBanner(TEST_LABEL_SLIM_CUSTOM_SERIALIZER);

        SimpleModule serializer = new SimpleModule("SlimCustomDeSerializer", new Version(1, 0, 0, null, null, null));
        serializer.addSerializer(CustomerSlim.class, new CustomerSlimSerializer());
        serializer.addDeserializer(CustomerSlim.class, new CustomerSlimDeserializer());
        mapper.registerModule(serializer);

        CustomerSlim[] slimOnes = CustomerSlim.fromCustomers(customers);
        byte[] plainJson = createPlainJson(TEST_LABEL_SLIM_CUSTOM_SERIALIZER, slimOnes);
        compressJson(TEST_LABEL_SLIM_CUSTOM_SERIALIZER, plainJson);
    }

    private void printBanner(String name) {
        System.out.println();
        System.out.println("************************************************");
        System.out.println("Testing " + name);
        System.out.println();
    }

    void compressJson(String label, byte[] plainJson) throws IOException {
        ByteArrayOutputStream outpuStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipStream = new GZIPOutputStream(outpuStream);
        gzipStream.write(plainJson);
        gzipStream.close();
        byte[] gzippedJson = outpuStream.toByteArray();
        System.out.println(label + " GZIPped length: " + LENGTH_FORMATTER.format(gzippedJson.length));
        assertTrue(plainJson.length > gzippedJson.length, label + " should be longer than GZIPped data");
    }

    private byte[] createPlainJson(String label, Object[] customers) throws IOException {
        System.out.println(label + " sample: ");
        ObjectWriter prettyWritter = mapper.writerWithDefaultPrettyPrinter();
        System.out.println(prettyWritter.writeValueAsString(customers[0]));

        byte[] feedback = mapper.writeValueAsBytes(customers);
        System.out.println(label + " length:         " + LENGTH_FORMATTER.format(feedback.length));
        assertTrue(feedback.length > 1, label + " should be there");

        String prefix = label.replaceAll(" ", "-")
            .toLowerCase();
        File tempFile = File.createTempFile("jon-optimization-" + prefix, ".json");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(feedback);
        fos.close();
        System.out.println(label + " file:           " + tempFile.toString());

        Object[] restoredOnes = mapper.readValue(feedback, customers.getClass());
        assertArrayEquals(TEST_LABEL_DEFAULT_JSON + ": restoring from JSON should work", customers, restoredOnes);

        return feedback;
    }

}
