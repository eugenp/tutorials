package baeldung.hexagonal.framework.service;

import baeldung.hexagonal.boundary.adapter.AdapterProvider;

public class ServiceProvider {
    private static AttendeePersistor attendeePersistor;
    public static AttendeePersistor getAttendeePersistor(){
        if(attendeePersistor == null){
            attendeePersistor = new AttendeePersistor(AdapterProvider.getAttendeePersistenceAdapter());
        }
        return attendeePersistor;
    }
}
