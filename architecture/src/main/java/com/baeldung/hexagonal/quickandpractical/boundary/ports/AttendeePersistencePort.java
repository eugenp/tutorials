package baeldung.hexagonal.boundary.ports;

import baeldung.hexagonal.core.AttendeeDto;

public interface AttendeePersistencePort {
    long save(AttendeeDto attendeeDto);
    AttendeeDto findById(Long id);
}
