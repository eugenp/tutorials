package baeldung.hexagonal.boundary.ports;

import java.util.List;

public interface MultiAttendeeCreatorPort {
    void createMultipleAttendees(List<String> names);
}
