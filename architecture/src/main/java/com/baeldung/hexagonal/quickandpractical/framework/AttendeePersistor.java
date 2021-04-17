package baeldung.hexagonal.framework;

import baeldung.hexagonal.core.AttendeeDto;
import baeldung.hexagonal.boundary.ports.AttendeePersistencePort;

public class AttendeePersistor {
    private AttendeePersistencePort attendeePersistencePort;
    public AttendeePersistor(AttendeePersistencePort attendeePersistencePort){
        this.attendeePersistencePort = attendeePersistencePort;
    }


    public long save(AttendeeDto attendeeDto){
        return attendeePersistencePort.save(attendeeDto);
    }

}
