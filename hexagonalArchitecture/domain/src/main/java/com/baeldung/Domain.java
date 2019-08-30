package com.baeldung;

import com.baeldung.service.OutputStreamingPort;

public class Domain {

    private OutputStreamingPort outputStreamingPort;

    public Domain(OutputStreamingPort outputStreamingPort) {
        this.outputStreamingPort = outputStreamingPort;
    }

    public void greeting() {
        outputStreamingPort.write("Hello World");
    }
}
