package com.baeldung.object.copy;

public class Author implements Cloneable {

    private String name;
    private String email;
    private Portal portal;

    public Author(String name, String email, Portal portal) {
        this.name = name;
        this.email = email;
        this.portal = portal;
    }

    public Author(Author author) {
        this.name = author.getName();
        this.email = author.getEmail();
        this.portal = new Portal(author.getPortal().getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setPortal(Portal portal) {
        this.portal = portal;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Author clonedAuthor = (Author) super.clone();
        clonedAuthor.portal = (Portal) portal.clone();
        return clonedAuthor;
    }

}