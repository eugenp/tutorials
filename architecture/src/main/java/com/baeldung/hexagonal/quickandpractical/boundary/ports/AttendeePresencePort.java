package baeldung.hexagonal.boundary.ports;

public interface AttendeePresencePort {
    boolean isPresent(long attendeeId);
    void setPresent(long attendeeId);
    void setAbsent(long attendeeId);
}
