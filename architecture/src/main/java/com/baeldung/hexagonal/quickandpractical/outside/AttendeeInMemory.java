package baeldung.hexagonal.outside;

import baeldung.hexagonal.core.AttendeeDto;

import java.util.HashMap;
import java.util.Map;

import baeldung.hexagonal.outside.entities.MemoryAttendeeEntity;
import baeldung.hexagonal.boundary.ports.AttendeePersistencePort;

public class AttendeeInMemory implements AttendeePersistencePort {
    private static Map<Long, MemoryAttendeeEntity> attendees = new HashMap<>();
    private static Long nextId = 0L;

    @Override
    public long save(AttendeeDto attendeeDto) {
        Long currentId = nextId;
        if(attendeeDto.getId() == null){
            attendees.put(nextId, new MemoryAttendeeEntity().setId(nextId).setName(attendeeDto.getName()).setPresent(attendeeDto.isPresent()));
            nextId++;//obviously this should be done in a different manner, by providing a global identityprovider, or a random UUID generator
        } else {
            MemoryAttendeeEntity persisted = attendees.get(attendeeDto.getId());
            persisted.setPresent(attendeeDto.isPresent()).setName(attendeeDto.getName());
        }
        System.out.println(attendees.get(currentId).toString());
        return currentId;
    }

    @Override
    public AttendeeDto findById(Long id) {
        AttendeeDto toReturn = new AttendeeDto();
        MemoryAttendeeEntity persisted = attendees.get(id);
        return toReturn.setId(persisted.getId()).setPresent(persisted.isPresent()).setName(persisted.getName());
    }
}
