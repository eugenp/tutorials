package com.baeldung.guava;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashingUnitTest {
    @Test
    public void whenHashingInSha384_hashFunctionShouldBeReturned() throws Exception {
        int inputData = 15;

        HashFunction hashFunction = Hashing.sha384();
        HashCode hashCode = hashFunction.hashInt(inputData);

        assertEquals("0904b6277381dcfbdddd6b6c66e4e3e8f83d4690718d8e6f272c891f24773a12feaf8c449fa6e42240a621b2b5e3cda8",
                hashCode.toString());
    }

    @Test
    public void whenConcatenatingHashFunction_concatenatedHashShouldBeReturned() throws Exception {
        int inputData = 15;

        HashFunction hashFunction = Hashing.concatenating(Hashing.crc32(), Hashing.crc32());
        HashFunction crc32Function = Hashing.crc32();

        HashCode hashCode = hashFunction.hashInt(inputData);
        HashCode crc32HashCode = crc32Function.hashInt(inputData);

        assertEquals(crc32HashCode.toString() + crc32HashCode.toString(), hashCode.toString());
    }
}
