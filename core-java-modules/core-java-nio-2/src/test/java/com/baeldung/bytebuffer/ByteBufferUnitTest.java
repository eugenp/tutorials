package com.baeldung.bytebuffer;

import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.*;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class ByteBufferUnitTest {
    @Test
    public void givenBufferCreation_whenUsingAllocate_thenSuccess() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertNotNull(buffer);
    }

    @Test
    public void givenBufferCreation_whenUsingAllocateDirect_thenSuccess() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        assertNotNull(buffer);
    }

    @Test
    public void givenBufferCreation_whenUsingWrap_thenSuccess() {
        byte[] bytes = new byte[10];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        assertNotNull(buffer);
    }

    @Test
    public void givenBufferIndex_whenUsingAllocate_thenInitialIndices() {
        // create instance using allocate
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // get index
        int position = buffer.position();
        int limit = buffer.limit();
        int capacity = buffer.capacity();

        // assert
        assertEquals(0, position);
        assertEquals(10, limit);
        assertEquals(10, capacity);
    }

    @Test
    public void givenBufferIndex_whenChangingPositionAndLimit_thenSuccess() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // change index
        buffer.position(2);
        buffer.limit(5);

        // assert
        assertIndex(buffer, -1, 2, 5, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingWrap_thenInitialIndices() {
        // create instance
        byte[] bytes = new byte[10];
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // assert
        assertIndex(buffer, -1, 0, 10, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingWrapWithOffsetAndLength_thenInitialIndices() {
        // create instance
        byte[] bytes = new byte[10];
        ByteBuffer buffer = ByteBuffer.wrap(bytes, 2, 6);

        // assert
        assertIndex(buffer, -1, 2, 8, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingMarkAndReset_thenOK() {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        buffer.position(2);
        assertIndex(buffer, -1, 2, 10, 10);

        buffer.mark();
        assertIndex(buffer, 2, 2, 10, 10);

        buffer.position(5);
        assertIndex(buffer, 2, 5, 10, 10);

        buffer.reset();
        assertIndex(buffer, 2, 2, 10, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingClear_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        // change index
        buffer.position(2);
        buffer.mark();
        buffer.position(5);
        buffer.limit(8);
        assertIndex(buffer, 2, 5, 8, 10);

        // clear
        buffer.clear();
        assertIndex(buffer, -1, 0, 10, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingFlip_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        // change index
        buffer.position(2);
        buffer.mark();
        buffer.position(5);
        buffer.limit(8);
        assertIndex(buffer, 2, 5, 8, 10);

        // flip
        buffer.flip();
        assertIndex(buffer, -1, 0, 5, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingRewind_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        // change index
        buffer.position(2);
        buffer.mark();
        buffer.position(5);
        buffer.limit(8);
        assertIndex(buffer, 2, 5, 8, 10);

        // rewind
        buffer.rewind();
        assertIndex(buffer, -1, 0, 8, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingCompact_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        // change index
        buffer.position(2);
        buffer.mark();
        buffer.position(5);
        buffer.limit(8);
        assertIndex(buffer, 2, 5, 8, 10);

        // compact
        buffer.compact();
        assertIndex(buffer, -1, 3, 10, 10);
    }

    @Test
    public void givenBufferIndex_whenUsingRemain_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);

        // change index
        buffer.position(2);
        buffer.limit(8);

        // remain
        boolean flag = buffer.hasRemaining();
        int remaining = buffer.remaining();

        // assert
        assertTrue(flag);
        assertEquals(6, remaining);
    }

    @Test(expected = BufferUnderflowException.class)
    public void givenNotEnoughRemaining_WhenCallingGetInt_thenBufferUnderflowException() {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.getInt();
    }

    @Test(expected = BufferOverflowException.class)
    public void givenNotEnoughRemaining_WhenCallingPutInt_thenBufferOverflowException() {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.putInt(10);
    }

    @Test
    public void givenBufferView_whenUsingDuplicate_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        // change index
        buffer.position(2);
        buffer.mark();
        buffer.position(5);
        buffer.limit(8);
        assertIndex(buffer, 2, 5, 8, 10);

        // view
        ByteBuffer view = buffer.duplicate();
        assertIndex(view, 2, 5, 8, 10);
    }

    @Test
    public void givenBufferView_whenUsingSlice_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        // change index
        buffer.position(2);
        buffer.mark();
        buffer.position(5);
        buffer.limit(8);
        assertIndex(buffer, 2, 5, 8, 10);

        // view
        ByteBuffer view = buffer.slice();
        assertIndex(view, -1, 0, 3, 3);
    }

    @Test
    public void givenBufferView_whenUsingAsReaOnlyBuffer_thenOK() {
        // create instance
        ByteBuffer buffer = ByteBuffer.allocate(10);
        assertIndex(buffer, -1, 0, 10, 10);

        // change index
        buffer.position(2);
        buffer.mark();
        buffer.position(5);
        buffer.limit(8);
        assertIndex(buffer, 2, 5, 8, 10);

        // view
        ByteBuffer view = buffer.asReadOnlyBuffer();
        assertIndex(view, 2, 5, 8, 10);
    }

    @Test
    public void givenBufferView_whenUsingAsIntBuffer_thenOK() {
        // create instance
        byte[] bytes = new byte[]{
                (byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE, // CAFEBABE ---> cafebabe
                (byte) 0xF0, (byte) 0x07, (byte) 0xBA, (byte) 0x11, // F007BA11 ---> football
                (byte) 0x0F, (byte) 0xF1, (byte) 0xCE               // 0FF1CE   ---> office
        };
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        assertIndex(buffer, -1, 0, 11, 11);

        // view
        IntBuffer intBuffer = buffer.asIntBuffer();
        int capacity = intBuffer.capacity();
        assertEquals(2, capacity);
        assertIndex(intBuffer, -1, 0, 2, 2);
    }

    @Test
    public void givenByteOrder_whenUsingBigEndian_thenOK() {
        // create instance
        byte[] bytes = new byte[]{(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // change byte order
        buffer.order(ByteOrder.BIG_ENDIAN);
        int val = buffer.getInt();

        // assert
        assertEquals(0xCAFEBABE, val);
    }

    @Test
    public void givenByteOrder_whenUsingLittleEndian_thenOK() {
        // create instance
        byte[] bytes = new byte[]{(byte) 0xCA, (byte) 0xFE, (byte) 0xBA, (byte) 0xBE};
        ByteBuffer buffer = ByteBuffer.wrap(bytes);

        // change byte order
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        int val = buffer.getInt();

        // assert
        assertEquals(0xBEBAFECA, val);
    }

    @Test
    public void givenComparing_whenUsingEqualsAndCompareTo_thenOK() {
        // create instance
        byte[] bytes1 = "World".getBytes(StandardCharsets.UTF_8);
        byte[] bytes2 = "HelloWorld".getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer1 = ByteBuffer.wrap(bytes1);
        ByteBuffer buffer2 = ByteBuffer.wrap(bytes2);

        // change index
        buffer2.position(5);

        // equals and compareTo
        boolean equal = buffer1.equals(buffer2);
        int result = buffer1.compareTo(buffer2);

        // assert
        assertTrue(equal);
        assertEquals(0, result);
    }

    private void assertIndex(Buffer buffer, int mark, int position, int limit, int capacity) {
        assertEquals(mark, getMark(buffer));
        assertEquals(position, buffer.position());
        assertEquals(limit, buffer.limit());
        assertEquals(capacity, buffer.capacity());
    }

    private int getMark(Buffer buffer) {
        try {
            Class<?> clazz = Buffer.class;
            Field f = clazz.getDeclaredField("mark");
            f.setAccessible(true);
            Object result = f.get(buffer);
            return (int) result;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
