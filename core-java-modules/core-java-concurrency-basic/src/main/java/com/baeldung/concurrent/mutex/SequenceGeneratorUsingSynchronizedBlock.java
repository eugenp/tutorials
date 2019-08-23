package com.baeldung.concurrent.mutex;

public class SequenceGeneratorUsingSynchronizedBlock extends SequenceGenerator {

    @Override
    public int getNextSequence() throws InterruptedException {
        synchronized (this) {
            return super.getNextSequence();
        }
    }

}
