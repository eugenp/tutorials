package baeldung.hexagon.ports;

import java.time.Instant;

public interface TimePort {
    void addEvent(Instant time, String event);
}
