package baeldung.hexagonal.outside;

import baeldung.hexagonal.boundary.adapter.AdapterProvider;
import baeldung.hexagonal.boundary.ports.SingleAttendeeCreatorPort;

public class CommandLineController {
    private SingleAttendeeCreatorPort singleAttendeeCreatorPort = AdapterProvider.getSingleAttendeeAdapter();

    private void run(){
        //UI loop goes here
        String name = "Baeldung";
        long id = singleAttendeeCreatorPort.createSingleAttendee(name);
        System.out.println("Created an attendee with id " + id);
    }
    public static void main(String[] args) {
        CommandLineController commandLineController = new CommandLineController();
        commandLineController.run();

    }

}
