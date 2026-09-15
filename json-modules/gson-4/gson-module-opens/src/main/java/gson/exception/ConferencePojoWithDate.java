package gson.exception;

import java.time.LocalDate;

public class ConferencePojoWithDate {

    private String name;
    private int numberOfParticipants;
    private LocalDate conferenceStart;

    public String getName() {
        return name;
    }

    public int getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfParticipants(int numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public LocalDate getConferenceStart() {
        return conferenceStart;
    }

    public void setConferenceStart(LocalDate conferenceStart) {
        this.conferenceStart = conferenceStart;
    }
}
