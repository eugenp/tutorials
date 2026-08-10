package gson.exception;

public class ConferencePojoWithValidation {

    private String name;
    private int numberOfParticipants;

    public ConferencePojoWithValidation(String name, int numberOfParticipants) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The conference name cannot be left blank!");
        }
        this.name = name;
        this.numberOfParticipants = numberOfParticipants;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }
}
