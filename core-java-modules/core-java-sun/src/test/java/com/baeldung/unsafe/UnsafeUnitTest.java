package com.baeldung.unsafe;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class UnsafeUnitTest {

    private Unsafe unsafe;

    @Before
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        unsafe = (Unsafe) f.get(null);
    }


    @Test
    public void givenClass_whenInitializeIt_thenShouldHaveDifferentStateWhenUseUnsafe() throws IllegalAccessException, InstantiationException {
        //when
        InitializationOrdering o1 = new InitializationOrdering();
        assertEquals(o1.getA(), 1);

        //when
        InitializationOrdering o3 = (InitializationOrdering) unsafe.allocateInstance(InitializationOrdering.class);
        assertEquals(o3.getA(), 0);
    }

    @Test
    public void givenPrivateMethod_whenUsingUnsafe_thenCanModifyPrivateField() throws NoSuchFieldException {
        //given
        SecretHolder secretHolder = new SecretHolder();

        //when
        Field f = secretHolder.getClass().getDeclaredField("SECRET_VALUE");
        unsafe.putInt(secretHolder, unsafe.objectFieldOffset(f), 1);

        //then
        assertTrue(secretHolder.secretIsDisclosed());
    }

    @Test(expected = IOException.class)
    public void givenUnsafeThrowException_whenThrowCheckedException_thenNotNeedToCatchIt() {
        unsafe.throwException(new IOException());
    }

    @Test
    @Ignore // Uncomment for local
    public void givenArrayBiggerThatMaxInt_whenAllocateItOffHeapMemory_thenSuccess() throws NoSuchFieldException, IllegalAccessException {
        //given
        long SUPER_SIZE = (long) Integer.MAX_VALUE * 2;
        OffHeapArray array = new OffHeapArray(SUPER_SIZE);

        //when
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            array.set((long) Integer.MAX_VALUE + i, (byte) 3);
            sum += array.get((long) Integer.MAX_VALUE + i);
        }

        long arraySize = array.size();

        array.freeMemory();

        //then
        assertEquals(arraySize, SUPER_SIZE);
        assertEquals(sum, 300);

    }

    @Test
    public void givenUnsafeCompareAndSwap_whenUseIt_thenCounterYildCorrectLockFreeResults() throws Exception {
        //given
        int NUM_OF_THREADS = 1_000;
        int NUM_OF_INCREMENTS = 10_000;
        ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);
        CASCounter casCounter = new CASCounter();

        //when
        IntStream.rangeClosed(0, NUM_OF_THREADS - 1)
                .forEach(i -> service.submit(() -> IntStream
                        .rangeClosed(0, NUM_OF_INCREMENTS - 1)
                        .forEach(j -> casCounter.increment())));

        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);

        //then
        assertEquals(NUM_OF_INCREMENTS * NUM_OF_THREADS, casCounter.getCounter());

    }

    class InitializationOrdering {
        private long a;

        public InitializationOrdering() {
            this.a = 1;
        }

        public long getA() {
            return this.a;
        }
    }

    class SecretHolder {
        private int SECRET_VALUE = 0;

        public boolean secretIsDisclosed() {
            return SECRET_VALUE == 1;
        }
    }

}
