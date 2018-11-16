package com.baeldung.jpa.stringcast;

import javax.persistence.*;

@SqlResultSetMapping(name = "textQueryMapping", classes = {
  @ConstructorResult(targetClass = DummyEntity.class, columns = {
    @ColumnResult(name = "text")
  })
})
@Entity
@Table(name = "dummy")
public class DummyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    public DummyEntity() {

    }

    public DummyEntity(String text) {
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
