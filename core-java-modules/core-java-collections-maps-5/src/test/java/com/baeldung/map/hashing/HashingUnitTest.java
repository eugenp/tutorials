package com.baeldung.map.hashing;

import com.google.common.base.Stopwatch;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.SplittableRandom;
import java.util.function.Supplier;

public class HashingUnitTest {

    public static final int SAMPLES = 1000000;
    private SplittableRandom random = new SplittableRandom();

    private String[] names = {"John", "Adam", "Suzie"};

    @Test
    void givenPrimitiveByteArrayKey_whenRetrievingFromMap_shouldRetrieveDifferentObjects() {
        // bad hashing example is prohibitively slow for bigger samples
//        Duration[] badHashing = testDuration(MemberWithBadHashing::new);
        Duration[] withId = testDuration(MemberWithId::new);
        Duration[] withObjects = testDuration(MemberWithObjects::new);
        Duration[] withIdAndName = testDuration(MemberWithIdAndName::new);

//        System.out.println("Inserting with bad hashing:");
//        System.out.println(badHashing[0]);
//        System.out.println("Getting with bad hashing:");
//        System.out.println(badHashing[1]);

        System.out.println("Inserting with id hashing:");
        System.out.println(withId[0]);
        System.out.println("Getting with id hashing:");
        System.out.println(withId[1]);

        System.out.println("Inserting with id and name hashing:");
        System.out.println(withIdAndName[0]);
        System.out.println("Getting with id and name hashing:");
        System.out.println(withIdAndName[1]);

        System.out.println("Inserting with Objects hashing:");
        System.out.println(withObjects[0]);
        System.out.println("Getting with Objects hashing:");
        System.out.println(withObjects[1]);
    }

    private String randomName() {
        return names[random.nextInt(2)];
    }

    private <T extends Member> Duration[] testDuration(Supplier<T> factory) {
        HashMap<T, String> map = new HashMap<>();
        Stopwatch stopwatch = Stopwatch.createUnstarted();

        stopwatch.start();
        for(int i = 0; i < SAMPLES; i++) {
            T member = factory.get();
            member.id = i;
            member.name = randomName();
            map.put(member, member.name);
        }
        stopwatch.stop();
        Duration elapsedInserting = stopwatch.elapsed();
        stopwatch.reset();

        stopwatch.start();
        for (T key : map.keySet()) {
            map.get(key);
        }
        stopwatch.stop();
        Duration elapsedGetting = stopwatch.elapsed();
        stopwatch.reset();

        return new Duration[]{elapsedInserting, elapsedGetting};
    }
}
