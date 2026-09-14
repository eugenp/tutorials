package com.baeldung.springai.todowritetool;

public record TodoItem(String id, String content, Status status, Priority priority) {
    public enum Status { pending, in_progress, completed }
    public enum Priority { low, medium, high }
}
