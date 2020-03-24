package restx.demo.domain;

public class Message {
    private String message;

    public String getMessage() {
        return message;
    }

    public Message setMessage(final String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
