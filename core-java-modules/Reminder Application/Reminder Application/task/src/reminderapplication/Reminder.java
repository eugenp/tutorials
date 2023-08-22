package reminderapplication;

public class Reminder {

    private static String REMINDER_FORMAT = "Reminder Text: %s; Delay: %d; Period: %d;";

    private final String name;
    private final int delay;
    private final int period;

    public Reminder(final String name, final int delay, final int period) {
        this.name = name;
        this.delay = delay;
        this.period = period;
    }

    public String getName() {
        return name;
    }

    public int getDelay() {
        return delay;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return REMINDER_FORMAT.formatted(name, delay, period);
    }
}
