package com.baeldung.deep_shallow_copy;

/**
 * @author amitkumar
 */
public class Blog implements Cloneable {
    private String title;
    private String content;
    private Author author;

    public Blog(String title, String content, Author author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Blog createCopy() {
        Author author = this.getAuthor();
        return new Blog(this.getTitle(), this.getContent(), new Author(author.getFirstName(), author.getLastName()));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Author author = new Author(this.getAuthor()
          .getFirstName(), this.getAuthor()
          .getLastName());
        Blog cloned = (Blog) super.clone();
        cloned.setAuthor(author);
        return cloned;
    }
}
