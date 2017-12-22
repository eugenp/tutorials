package com.baeldung.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.logging.Logger;

@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;
  
  @NotNull @Size(min = 10)
  @Column(columnDefinition = "longtext")
  private String post;
  
  @NotNull @Size(min = 5, max = 100)
  @Column(unique = true)
  private String title;
  
  @NotNull
  @ManyToOne(targetEntity = User.class, optional = false)
  private User author;
  
  @Column(name = "created_at")
  private Date createdAt;
  
  @Column(name = "updated_at")
  private Date updatedAt;
  
  @Transient
  private Logger logger = Logger.getLogger(getClass().getName());
  
  public Post() {
    createdAt = new Date();
    updatedAt = new Date();
  }
  
  public Post(String title, String post, User author) {
    this.title = title;
    this.post = post;
    this.author = author;
    createdAt = new Date();
    updatedAt = new Date();
  }

    public int getId() {
        return id;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "title: " + this.title
                + "\npost: " + this.post
                + "\nauthor: " + this.author
                +"\ncreatetdAt: " + this.createdAt
                + "\nupdatedAt: " + this.updatedAt;
    }
}
