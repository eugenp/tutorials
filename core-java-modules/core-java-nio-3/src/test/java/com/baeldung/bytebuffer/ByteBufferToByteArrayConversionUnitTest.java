package com.baeldung.bytebuffer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

import org.junit.jupiter.api.Test;

class ByteBufferToByteArrayConversionUnitTest {
    
    @Test
    void givenByteBuffer_whenUsingArrayMethod_thenConvertToByteArray() {
        byte[] givenBytes = {1, 6, 3};
        ByteBuffer buffer = ByteBuffer.wrap(givenBytes);
        byte[] bytes = buffer.array();
        
        assertArrayEquals(givenBytes, bytes);
    }
    
    @Test
    void givenBufferWithoutBackingArray_whenCallingArray_thenThrowUnsupportedOperationException() {
        ByteBuffer buffer = ByteBuffer.allocateDirect(4);

        assertThrows(UnsupportedOperationException.class, buffer::array);
    }
    
    @Test
    void givenReadOnlyBuffer_whenCallingArray_thenThrowReadOnlyBufferException() {
        ByteBuffer buffer = ByteBuffer.wrap(new byte[] {1, 2, 3});
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        assertThrows(ReadOnlyBufferException.class, readOnlyBuffer::array);
    }
    
    @Test
    void givenByteBuffer_whenUsingGetMethod_thenConvertToByteArray() {
        byte[] givenBytes = {5, 4, 2};
        ByteBuffer buffer = ByteBuffer.wrap(givenBytes);
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        
        assertArrayEquals(givenBytes, bytes);
    }
    
    @Test
    void givenByteBuffer_whenUsingGetWithParamsMethod_thenConvertToByteArray() {
        byte[] givenBytes = {5, 4, 2, 7};
        ByteBuffer buffer = ByteBuffer.wrap(givenBytes);
        byte[] bytes = new byte[2];
        buffer.get(bytes, 0, 2);
        
        assertArrayEquals(new byte[] {5, 4}, bytes);
    }
    
    @Test
    void givenByteArray_whenUsingWrapMethod_thenConvertToByteBuffer() {
        byte[] givenBytes = {1, 2, 3};
        ByteBuffer buffer = ByteBuffer.wrap(givenBytes);
        
        assertArrayEquals(givenBytes, buffer.array());
    }
    
    @Test
    void givenByteArray_whenUsingWrapWithParamsMethod_thenConvertToByteBuffer() {
        byte[] givenBytes = {1, 2, 3, 7, 8};
        ByteBuffer buffer = ByteBuffer.wrap(givenBytes, 1, 2);
        
        byte[] actualBytes = new byte[buffer.remaining()];
        buffer.get(actualBytes);
        
        assertArrayEquals(new byte[] {2, 3}, actualBytes);
    }
    
    @Test
    void givenByteArray_whenUsingAllocateAndPutMethods_thenConvertToByteBuffer() {
        byte[] givenBytes = {1, 9, 7};
        ByteBuffer buffer = ByteBuffer.allocate(givenBytes.length);
        buffer.put(givenBytes);
        
        assertArrayEquals(givenBytes, buffer.array());
    }
    
    @Test
    void givenByteArray_whenUsingPutWithFlip_thenCorrect() {
        byte[] givenBytes = {3, 8};
        ByteBuffer buffer = ByteBuffer.allocate(givenBytes.length);
        buffer.put(givenBytes);
        buffer.flip();
        
        assertEquals(3, buffer.get());
    }
    
    @Test
    void givenByteArray_whenUsingPutWithoutFlip_thenThrowBufferUnderflowException() {
        byte[] givenBytes = {1, 5};
        ByteBuffer buffer = ByteBuffer.allocate(givenBytes.length);
        buffer.put(givenBytes);
        
        assertThrows(BufferUnderflowException.class, buffer::get);
    }
}
