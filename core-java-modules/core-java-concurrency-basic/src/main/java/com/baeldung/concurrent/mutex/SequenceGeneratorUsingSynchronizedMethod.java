package com.baeldung.concurrent.mutex;

public class SequenceGeneratorUsingSynchronizedMethod extends SequenceGenerator {

    @Override
    public synchronized int getNextSequence() throws InterruptedException {
        return super.getNextSequence();
    }

}
