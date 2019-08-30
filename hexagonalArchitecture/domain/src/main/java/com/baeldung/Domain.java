package com.baeldung;

import com.baeldung.service.GreetingPort;
import com.baeldung.service.NamingPort;
import com.baeldung.service.OutputStreamingPort;
import com.baeldung.service.TranslatingPort;

public class Domain {

    private final OutputStreamingPort outputStreamingPort;
    private final NamingPort namingPort;
    private final GreetingPort greetingPort;
    private final TranslatingPort translatingPort;

    public Domain(OutputStreamingPort outputStreamingPort, NamingPort namingPort,
                  GreetingPort greetingPort, TranslatingPort translatingPort) {
        this.outputStreamingPort = outputStreamingPort;
        this.namingPort = namingPort;
        this.greetingPort = greetingPort;
        this.translatingPort = translatingPort;
    }

    public void greeting() {
        String name = namingPort.getName();
        String greeting = greetingPort.getGreetingPhrase();
        String lang = greetingPort.getDesiredLanguage();
        String translatedGreeting = translatingPort.translate(greeting, lang);
        outputStreamingPort.write(String.format("%s %s", translatedGreeting, name));
    }
}
