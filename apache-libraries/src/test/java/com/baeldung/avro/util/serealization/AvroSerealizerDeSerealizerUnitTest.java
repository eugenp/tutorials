package com.baeldung.avro.util.serealization;

import com.baeldung.avro.util.model.Active;
import com.baeldung.avro.util.model.AvroHttpRequest;
import com.baeldung.avro.util.model.ClientIdentifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

public class AvroSerealizerDeSerealizerUnitTest {

    AvroSerealizer serealizer;
    AvroDeSerealizer deSerealizer;
    AvroHttpRequest request;

    @Before
    public void setUp() throws Exception {
        serealizer = new AvroSerealizer();
        deSerealizer = new AvroDeSerealizer();

        ClientIdentifier clientIdentifier = ClientIdentifier.newBuilder()
            .setHostName("localhost")
            .setIpAddress("255.255.255.0")
            .build();

        List<CharSequence> employees = new ArrayList();
        employees.add("James");
        employees.add("Alice");
        employees.add("David");
        employees.add("Han");

        request = AvroHttpRequest.newBuilder()
            .setRequestTime(01l)
            .setActive(Active.YES)
            .setClientIdentifier(clientIdentifier)
            .setEmployeeNames(employees)
            .build();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void WhenSerializedUsingJSONEncoder_thenObjectGetsSerialized() {
        byte[] data = serealizer.serealizeAvroHttpRequestJSON(request);
        assertTrue(Objects.nonNull(data));
        assertTrue(data.length > 0);
    }

    @Test
    public void WhenSerializedUsingBinaryEncoder_thenObjectGetsSerialized() {
        byte[] data = serealizer.serealizeAvroHttpRequestBinary(request);
        assertTrue(Objects.nonNull(data));
        assertTrue(data.length > 0);
    }

    @Test
    public void WhenDeserializeUsingJSONDecoder_thenActualAndExpectedObjectsAreEqual() {
        byte[] data = serealizer.serealizeAvroHttpRequestJSON(request);
        AvroHttpRequest actualRequest = deSerealizer.deSerealizeAvroHttpRequestJSON(data);
        assertEquals(actualRequest, request);
        assertTrue(actualRequest.getRequestTime()
            .equals(request.getRequestTime()));
    }

    @Test
    public void WhenDeserializeUsingBinaryecoder_thenActualAndExpectedObjectsAreEqual() {
        byte[] data = serealizer.serealizeAvroHttpRequestBinary(request);
        AvroHttpRequest actualRequest = deSerealizer.deSerealizeAvroHttpRequestBinary(data);
        assertEquals(actualRequest, request);
        assertTrue(actualRequest.getRequestTime()
            .equals(request.getRequestTime()));
    }
    
}

