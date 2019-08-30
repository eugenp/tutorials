package com.baeldung;

import com.baeldung.service.NamingPort;
import com.baeldung.service.OutputStreamingPort;

public class Domain {

    private final OutputStreamingPort outputStreamingPort;
    private final NamingPort namingPort;

    public Domain(OutputStreamingPort outputStreamingPort, NamingPort namingPort) {
        this.outputStreamingPort = outputStreamingPort;
        this.namingPort = namingPort;
    }

    public void greeting() {
        String name = namingPort.getName();
        outputStreamingPort.write(String.format("%s %s","Hello", name));
    }
}
