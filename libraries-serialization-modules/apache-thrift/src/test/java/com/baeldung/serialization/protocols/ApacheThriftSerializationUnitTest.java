package com.baeldung.serialization.protocols;

import static org.junit.Assert.assertEquals;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TMemoryBuffer;
import org.junit.jupiter.api.Test;

public class ApacheThriftSerializationUnitTest {

    @Test
    public void whenUsingThriftForSerialization_ThenDataIsSameAfterDeserialization() throws TException {
        
        User user = new User();
        user.setId(2);
        user.setName("Greg");

        TMemoryBuffer trans = new TMemoryBuffer(4096);
        TProtocol proto = new TBinaryProtocol(trans);
        
        proto.writeI32(user.getId());
        proto.writeString(user.getName());
        
        int userId = proto.readI32();
        String userName = proto.readString();
        
        assertEquals(2, userId);
        assertEquals("Greg", userName);
    }
}