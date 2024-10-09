package com.itsnaveenk.springkafkaelasticdemo.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "notification", createIndex = true)
public class NotificationEntity {

    @Id
    private int user_id;

    private String message;

    private int recipient_id;

    public NotificationEntity() {
    }

    public NotificationEntity(int user_id, String message, int recipient_id) {
        this.user_id = user_id;
        this.message = message;
        this.recipient_id = recipient_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRecipient_id() {
        return recipient_id;
    }

    public void setRecipient_id(int recipient_id) {
        this.recipient_id = recipient_id;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "user_id=" + user_id +
                ", message='" + message + '\'' +
                ", recipient_id=" + recipient_id +
                '}';
    }
}
