package dto;

/**
 * Created by alfred on 07 October 2019
 */
public class RequestDTO {
    private String message;

    public RequestDTO() {
    }

    public RequestDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "message='" + message + '\'' +
                '}';
    }
}
