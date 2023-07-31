package com.baeldung.concurrent.futurevscompletablefuturevsrxjava;

public class ObjectHydrator {

    public TestObject hydrateTestObject(TestObject testObject){
        testObject.setDataPointTwo(20);
        return testObject;
    }

}
