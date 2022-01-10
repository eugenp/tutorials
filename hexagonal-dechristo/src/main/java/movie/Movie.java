package movie;

public class Movie {
    private Integer id;
    private String title;
    private Integer releaseYear;
    private String genre;


    public Movie(Integer id, String title, Integer releaseYear, String genre) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }
}
