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

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

class JsonOptimizationUnitTest {
    private static final String TEST_LABEL_JACKSON_DEFAULT_OPTIONS = "Default JSON";
    private static final String TEST_LABEL_DEFAULT_JSON_NO_NULL = "Default JSON without null";
    private static final String TEST_LABEL_SHORTER_FIELD_NAMES = "Shorter field names";
    private static final String TEST_LABEL_SHORTER_FIELD_NAMES_AND_NO_NULL = "Shorter field names without null";
    private static final String TEST_LABEL_SERIALIZING_TO_ARRAY = "Custom serializer";
    private static final String TEST_LABEL_SLIM_CUSTOM_SERIALIZER = "Slim custom serializer";
    private static final String TEST_LABEL_SLIM_CUSTOMER = "Slim customer";
    private static final String TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES = "Slim customer with shorter field names";
    private static DecimalFormat LENGTH_FORMATTER = new DecimalFormat("###,###.0");
    private static DecimalFormat PERCENT_FORMATTER = new DecimalFormat("###.0");
    private static Customer[] customers;
    private ObjectMapper mapper;
    private static int defaultJsonLength;

    @BeforeAll
    static void setUpOnce() throws Exception {
        customers = Customer.fromMockFile();
        ObjectMapper oneTimeMapper = new ObjectMapper();
        byte[] feedback = oneTimeMapper.writeValueAsBytes(customers);
        defaultJsonLength = feedback.length;
        System.out.println();        
        System.out.println("Default JSON length: " + defaultJsonLength);        
        System.out.println();        
    }

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void whenSetUp_ThenOneThousandCustomers() {
        assertEquals(1000, customers.length, "There should be a 1000 customers");
    }

    @Test
    void whenJacksonDefaultOptions_thenValid() throws IOException {
        printBanner(TEST_LABEL_JACKSON_DEFAULT_OPTIONS);
        byte[] plainJson = createJsonAndVerify(TEST_LABEL_JACKSON_DEFAULT_OPTIONS, customers);
        compressJson(TEST_LABEL_JACKSON_DEFAULT_OPTIONS, plainJson);
    }

    @Test
    void whenExcludingNull_thenValid() throws IOException {
        printBanner(TEST_LABEL_DEFAULT_JSON_NO_NULL);
        mapper.setSerializationInclusion(Include.NON_NULL);
        byte[] plainJson = createJsonAndVerify(TEST_LABEL_DEFAULT_JSON_NO_NULL, customers);
        compressJson(TEST_LABEL_DEFAULT_JSON_NO_NULL, plainJson);
    }

    @Test
    void whenShorterFieldNames_thenValid() throws IOException {
        printBanner(TEST_LABEL_SHORTER_FIELD_NAMES);
        CustomerShortNames[] shorterOnes = CustomerShortNames.fromCustomers(customers);
        byte[] shorterJson = createJsonAndVerify(TEST_LABEL_SHORTER_FIELD_NAMES, shorterOnes);
        compressJson(TEST_LABEL_SHORTER_FIELD_NAMES, shorterJson);
    }

    @Test
    void whenShorterFieldNamesAndExcludingNull_thenValid() throws IOException {
        printBanner(TEST_LABEL_SHORTER_FIELD_NAMES_AND_NO_NULL);
        CustomerShortNames[] shorterOnes = CustomerShortNames.fromCustomers(customers);
        mapper.setSerializationInclusion(Include.NON_NULL);
        byte[] shorterJson = createJsonAndVerify(TEST_LABEL_SHORTER_FIELD_NAMES_AND_NO_NULL, shorterOnes);
        compressJson(TEST_LABEL_SHORTER_FIELD_NAMES_AND_NO_NULL, shorterJson);
    }

    @Test
    void whenSlimCustomer_thenValid() throws IOException {
        printBanner(TEST_LABEL_SLIM_CUSTOMER);
        CustomerSlim[] slimOnes = CustomerSlim.fromCustomers(customers);
        byte[] slimJson = createJsonAndVerify(TEST_LABEL_SLIM_CUSTOMER, slimOnes);
        compressJson(TEST_LABEL_SLIM_CUSTOMER, slimJson);
    }

    @Test
    void whenSlimCustomerAndShorterFieldNames_thenValid() throws IOException {
        printBanner(TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES);
        CustomerSlimShortNames[] slimOnes = CustomerSlimShortNames.fromCustomers(customers);
        byte[] slimJson = createJsonAndVerify(TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES, slimOnes);
        compressJson(TEST_LABEL_SLIM_CUSTOMER_SHORT_NAMES, slimJson);
    }

    @Test
    void whenSerializingToArray_thenValid() throws IOException {
        printBanner(TEST_LABEL_SERIALIZING_TO_ARRAY);
        SimpleModule serializer = new SimpleModule("CustomDeSerializer", new Version(1, 0, 0, null, null, null));
        serializer.addSerializer(Customer.class, new CustomerSerializer());
        serializer.addDeserializer(Customer.class, new CustomerDeserializer());
        mapper.registerModule(serializer);

        byte[] plainJson = createJsonAndVerify(TEST_LABEL_SERIALIZING_TO_ARRAY, customers);
        compressJson(TEST_LABEL_SERIALIZING_TO_ARRAY, plainJson);
    }

    @Test
    void whenSerializingToArrayAndSlimCustomer_thenValid() throws IOException {
        printBanner(TEST_LABEL_SLIM_CUSTOM_SERIALIZER);
        SimpleModule serializer = new SimpleModule("SlimCustomDeSerializer", new Version(1, 0, 0, null, null, null));
        serializer.addSerializer(CustomerSlim.class, new CustomerSlimSerializer());
        serializer.addDeserializer(CustomerSlim.class, new CustomerSlimDeserializer());
        mapper.registerModule(serializer);

        CustomerSlim[] slimOnes = CustomerSlim.fromCustomers(customers);
        byte[] plainJson = createJsonAndVerify(TEST_LABEL_SLIM_CUSTOM_SERIALIZER, slimOnes);
        compressJson(TEST_LABEL_SLIM_CUSTOM_SERIALIZER, plainJson);
    }

    private void printBanner(String name) {
        System.out.println();
        System.out.println("************************************************");
        System.out.println("Testing " + name);
        System.out.println();
    }

    void compressJson(String label, byte[] plainJson) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipStream = new GZIPOutputStream(outputStream);
        gzipStream.write(plainJson);
        gzipStream.close();
        outputStream.close();
        byte[] gzippedJson = outputStream.toByteArray();
        double length = gzippedJson.length / 1024d;
        double percent = gzippedJson.length * 100d / defaultJsonLength;
        System.out.println(label + " GZIPped length: " + LENGTH_FORMATTER.format(length) 
        + "kB (" + PERCENT_FORMATTER.format(percent) + "%)");
        assertTrue(plainJson.length > gzippedJson.length, label + " should be longer than GZIPped data");
    }

    private byte[] createJsonAndVerify(String label, Object[] customers) throws IOException {
        System.out.println(label + " sample: ");
        ObjectWriter prettyWritter = mapper.writerWithDefaultPrettyPrinter();
        System.out.println(prettyWritter.writeValueAsString(customers[0]));

        byte[] feedback = mapper.writeValueAsBytes(customers);
        double length = feedback.length / 1024d;
        double percent = feedback.length * 100d / defaultJsonLength;
        System.out.println(label + " length:         " + LENGTH_FORMATTER.format(length) 
        + "kB (" + PERCENT_FORMATTER.format(percent) + "%)");
        assertTrue(feedback.length > 1, label + " should be there");

        String prefix = label.replaceAll(" ", "-")
            .toLowerCase();
        File tempFile = File.createTempFile("jon-optimization-" + prefix, ".json");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(feedback);
        fos.close();
        System.out.println(label + " file:           " + tempFile.toString());

        Object[] restoredOnes = mapper.readValue(feedback, customers.getClass());
        assertArrayEquals(TEST_LABEL_JACKSON_DEFAULT_OPTIONS + ": restoring from JSON should work", customers, restoredOnes);

        return feedback;
    }

}
