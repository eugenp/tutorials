package com.baeldung.domain;

import java.time.LocalDate;

public class TodoItem {
    private int id;
    private String title;
    private String details;
    private ItemPriority itemPriority;

    public TodoItem(String title, String details, ItemPriority itemPriority) {
        this.title = title;
        this.details = details;
        this.itemPriority = itemPriority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public ItemPriority getItemPriority() {
        return itemPriority;
    }

    public void setItemPriority(ItemPriority itemPriority) {
        this.itemPriority = itemPriority;
    }

    @Override
    public String toString() {
        return "TodoItem{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", details='" + details + '\'' +
                ", itemPriority=" + itemPriority +
                '}';
    }

}
