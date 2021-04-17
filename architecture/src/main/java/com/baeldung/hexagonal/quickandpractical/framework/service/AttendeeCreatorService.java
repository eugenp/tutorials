package baeldung.hexagonal.framework.service;

import baeldung.hexagonal.boundary.ports.*;
import baeldung.hexagonal.core.AttendeeDto;

import java.util.List;

public class AttendeeCreatorService implements SingleAttendeeCreatorPort, MultiAttendeeCreatorPort {
    //this can be constructor dependency injected, as well as using spring @Autowired or JEE @EJB alternatives
    private AttendeePersistor attendeePersistor;

    public AttendeeCreatorService(AttendeePersistor attendeePersistor){
        this.attendeePersistor = attendeePersistor;
    }

    public long createSingleAttendee(String name){
        AttendeeDto newAttendee = new AttendeeDto().setName(name);

        return attendeePersistor.save(newAttendee);
    }

    public void createMultipleAttendees(List<String> names){
        names.forEach(name -> attendeePersistor.save(new AttendeeDto().setName(name)));
    }
}
