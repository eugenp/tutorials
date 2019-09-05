package service.impl;

import service.GreetingPort;
import service.OutputStreamingPort;

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
