package baeldung.hexagon.ports;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTimePort implements TimePort {

    private final ConcurrentHashMap<Instant, List<String>> events = new ConcurrentHashMap<>();

    @Override
    public void addEvent(Instant time, String event) {
        events.compute(time, (k, v) -> {
            if (v == null) {
                List<String> list = new ArrayList<>();
                list.add(event);
                return list;
            }

            v.add(event);
            return v;
        });
    }
}
