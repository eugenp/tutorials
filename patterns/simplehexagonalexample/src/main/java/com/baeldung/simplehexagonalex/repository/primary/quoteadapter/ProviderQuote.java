package com.baeldung.simplehexagonalex.repository.primary.quoteadapter;

import java.util.List;
import java.util.Objects;

public class ProviderQuote {

    private String quote;
    private String length;
    private String author;
    private List<String> tags;
    private String category;
    private String language;
    private String date;
    private String permalink;
    private String id;
    private String background;
    private String title;

    public ProviderQuote() {

    }

    public ProviderQuote(String quote, String length, String author, List<String> tags, String category, String language, String date, String permalink, String id, String background, String title) {
        super();
        this.quote = quote;
        this.length = length;
        this.author = author;
        this.tags = tags;
        this.category = category;
        this.language = language;
        this.date = date;
        this.permalink = permalink;
        this.id = id;
        this.background = background;
        this.title = title;
    }

    public String getQuote() {
        return quote;
    }

    public String getLength() {
        return length;
    }

    public String getAuthor() {
        return author;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getCategory() {
        return category;
    }

    public String getLanguage() {
        return language;
    }

    public String getDate() {
        return date;
    }

    public String getPermalink() {
        return permalink;
    }

    public String getId() {
        return id;
    }

    public String getBackground() {
        return background;
    }

    public String getTitle() {
        return title;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ProviderQuote other = (ProviderQuote) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "TheysaysoQuote [quote=" + quote + ", length=" + length + ", " + "author=" + author + ", tags=" + tags + ", category=" + category + ", language=" + language + ", date=" + date + ", permalink=" + permalink + ", id=" + id + ", background="
            + background + ", title=" + title + "]";
    }
}
