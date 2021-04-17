package baeldung.hexagonal.boundary.adapter;

import baeldung.hexagonal.boundary.ports.AttendeePersistencePort;
import baeldung.hexagonal.boundary.ports.SingleAttendeeCreatorPort;
import baeldung.hexagonal.framework.service.AttendeeCreatorService;
import baeldung.hexagonal.framework.service.ServiceProvider;
import baeldung.hexagonal.outside.AttendeeInMemory;

public class AdapterProvider {
    private static AttendeePersistencePort attendeePersistenceAdapter;
    private static SingleAttendeeCreatorPort singleAttendeeCreatorPort;



    public static AttendeePersistencePort getAttendeePersistenceAdapter(){
        if(attendeePersistenceAdapter == null){
            attendeePersistenceAdapter = new AttendeeInMemory();
        }
        return attendeePersistenceAdapter;
    }

    public static SingleAttendeeCreatorPort getSingleAttendeeAdapter() {
        if(singleAttendeeCreatorPort == null){
            singleAttendeeCreatorPort = new AttendeeCreatorService(ServiceProvider.getAttendeePersistor());
        }
        return singleAttendeeCreatorPort;
    }
}
