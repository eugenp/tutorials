package com.baeldung.heaxagonal.arch.domain;

import com.baeldung.heaxagonal.arch.port.InboundPort;
import com.baeldung.heaxagonal.arch.port.OutboundPortGet;
import com.baeldung.heaxagonal.arch.port.OutboundPortPrint;

import java.util.Random;

public class Domain implements InboundPort {

    private Random random = new Random();

    private OutboundPortGet outboundPortGet;
    private OutboundPortPrint outboundPortPrint;

    public Domain(OutboundPortGet outboundPortGet, OutboundPortPrint outboundPortPrint) {
        this.outboundPortGet = outboundPortGet;
        this.outboundPortPrint = outboundPortPrint;
    }

    @Override
    public void execute() {
        String[] strings = outboundPortGet.execute();
        String[] randomized = randomize(strings);
        outboundPortPrint.execute(randomized);
    }

    private String[] randomize(String[] strings) {
        int low = 0;
        int max = strings.length;

        int randomInt = getRandomInt(low, max);
        String[] randomized = new String[randomInt];

        for (int i = 0; i < randomized.length; i++) {
            randomized[i] = strings[getRandomInt(low, max)];
        }

        return randomized;
    }

    private int getRandomInt(int low, int max) {
        return low + (int) (random.nextFloat() * (max - low));
    }
}
