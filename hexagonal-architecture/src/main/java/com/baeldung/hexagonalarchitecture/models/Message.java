package com.baeldung.hexagonalarchitecture.models;


import lombok.Getter;
import javax.persistence.*;


@Getter
@Entity
@Table(name = "message")
public class Message {

    public static final int MESSAGE_SOURCE_FB = 1;
    public static final int MESSAGE_SOURCE_TWITTER = 2;

    public Message() {
    }

    public Message(String message, int source) {
        this.message = message;
        this.source = source;
    }

    @GeneratedValue
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private Long id;


    @Column(name = "message", nullable = false, length = 500)
    private String message;

    @Column(name = "source", nullable = false)
    private int source;
}
