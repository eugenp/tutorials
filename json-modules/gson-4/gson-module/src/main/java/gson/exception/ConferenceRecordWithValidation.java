package gson.exception;

public record ConferenceRecordWithValidation(String name, int numberOfParticipants) {

    public ConferenceRecordWithValidation {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The conference name cannot be left blank!");
        }
    }
}
