package groupedvsmultipleassertions;

public class User {
    String username;
    String email;
    Boolean activated;

    public User(String username, String email, Boolean activated) {
        this.username = username;
        this.email = email;
        this.activated = activated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }
}
