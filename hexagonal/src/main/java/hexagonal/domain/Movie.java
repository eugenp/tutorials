package hexagonal.domain;

public class Movie {
    private String title;
    private Genre genre;

    public enum Genre {
        COMEDY,
        HORROR
    }

    public Movie(String title, Genre genre) {
        this.title = title;
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public Genre getGenre() {
        return genre;
    }
}
