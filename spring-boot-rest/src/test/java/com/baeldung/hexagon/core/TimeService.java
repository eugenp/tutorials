package baeldung.hexagon.core;

import java.time.Instant;

public interface TimeService {

    String currentTime(String format);

    void addEvent(Instant time, String event);
}
