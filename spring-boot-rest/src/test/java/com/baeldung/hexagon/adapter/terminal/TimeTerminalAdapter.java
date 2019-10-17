package baeldung.hexagon.adapter.terminal;

import baeldung.hexagon.core.SimpleTimeService;
import baeldung.hexagon.core.TimeService;
import baeldung.hexagon.ports.InMemoryTimePort;

public class TimeTerminalAdapter {
    private static final TimeService timeService = new SimpleTimeService(new InMemoryTimePort());

    public static void main(String[] args) {
        String format = "yyy-MM-dd";
        if (args.length > 0) {
            format = args[0];
        }

        System.out.println("The current time is: " + timeService.currentTime(format));
    }
}
