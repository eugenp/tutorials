package app.models;

public class Article {

    private String title;
    private String author;
    private String words;
    private String date;

    public Article(String title, String author, String words, String date) {
        super();
        this.title = title;
        this.author = author;
        this.words = words;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}