package baeldung.hexagon.core;

import baeldung.hexagon.ports.TimePort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class SimpleTimeService implements TimeService {

    private final TimePort timePort;

    public SimpleTimeService(TimePort timePort) {
        this.timePort = timePort;
    }

    @Override
    public String currentTime(String format) {
        return DateTimeFormatter.ofPattern(format).format(LocalDateTime.now());
    }

    @Override
    public void addEvent(Instant time, String event) {
        timePort.addEvent(time, event);
    }
}
