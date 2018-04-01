package com.midgetontoes.todolist;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied when requesting the resource.");
    }
}
