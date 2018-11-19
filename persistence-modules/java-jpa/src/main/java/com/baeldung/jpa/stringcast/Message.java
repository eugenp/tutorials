package com.baeldung.jpa.stringcast;

import javax.persistence.*;

@SqlResultSetMapping(name = "textQueryMapping", classes = {
  @ConstructorResult(targetClass = Message.class, columns = {
    @ColumnResult(name = "text")
  })
})
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public Message() {

    }

    public Message(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
