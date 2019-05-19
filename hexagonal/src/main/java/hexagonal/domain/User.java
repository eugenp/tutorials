package hexagonal.domain;

public class User {
    private String name;
    private String preferredGenre;

    public User(String name, String preferredGenre) {
        this.name = name;
        this.preferredGenre = preferredGenre;
    }

    public String getName() {
        return name;
    }

    public String getPreferredGenre() {
        return preferredGenre;
    }
}
