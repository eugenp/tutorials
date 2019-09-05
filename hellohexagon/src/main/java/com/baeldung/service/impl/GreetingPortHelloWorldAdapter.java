package com.baeldung.service.impl;

import com.baeldung.service.GreetingPort;
import com.baeldung.service.OutputStreamingPort;

public class GreetingPortHelloWorldAdapter implements GreetingPort {

    private OutputStreamingPort outputStreamingPort;

    public GreetingPortHelloWorldAdapter() {
        this.outputStreamingPort = new OutputStreamingPortConsoleAdapter();
    }

    public GreetingPortHelloWorldAdapter(OutputStreamingPort outputStreamingPort) {
        this.outputStreamingPort = outputStreamingPort;
    }

    @Override
    public void greet() {
        this.outputStreamingPort.write("Hello World");
    }
}
