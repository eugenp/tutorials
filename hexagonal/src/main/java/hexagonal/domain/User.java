package hexagonal.domain;

public class User {
    private String name;
    private Movie.Genre preferredGenre;

    public User(String name, Movie.Genre preferredGenre) {
        this.name = name;
        this.preferredGenre = preferredGenre;
    }

    public String getName() {
        return name;
    }

    public Movie.Genre getPreferredGenre() {
        return preferredGenre;
    }
}
