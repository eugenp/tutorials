package com.baeldung.objectId;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.ByteBuffer;
import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

public class SameObjectIdUnitTest {

    @Test
    public void givenSameDateAndCounter_whenComparingObjectIds_thenTheyAreNotEqual() {
        final Date date = new Date();
        final ObjectId objectIdDate = new ObjectId(date);
        final ObjectId objectIdDateCounter1 = new ObjectId(date, 100);
        final ObjectId objectIdDateCounter2 = new ObjectId(date, 100);

        assertThat(objectIdDate).isNotEqualTo(objectIdDateCounter1);
        assertThat(objectIdDate).isNotEqualTo(objectIdDateCounter2);

        assertThat(objectIdDateCounter1).isEqualTo(objectIdDateCounter2);
    }

    @Test
    public void givenSameArrayOfBytes_whenComparingObjectIdsCreatedViaDifferentMethods_thenTheObjectIdsAreEqual() {
        final byte[] bytes = "123456789012".getBytes();
        final ObjectId objectIdBytes = new ObjectId(bytes);

        final ByteBuffer buffer = ByteBuffer.wrap(bytes);
        final ObjectId objectIdByteBuffer = new ObjectId(buffer);

        assertThat(objectIdBytes).isEqualTo(objectIdByteBuffer);
    }
}
