package org.copyobjects.deepcopy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Book implements Cloneable{
    private String name;
    private int pageCount;
    private Author author;

    public void print(){
        System.out.println(this.name + " = (name: " + name + ", pageCount: " + pageCount
                + ", author:(authorName: " + author.getAuthorName() + ", email: " + author.getEmail()
                + ", hashcode: " + this.getAuthor().hashCode() + ")");
    }

    @Override
    public Book clone() throws CloneNotSupportedException{
        Book book = (Book) super.clone();
        book.setAuthor((Author) book.getAuthor().clone());
        return book;
    }
}