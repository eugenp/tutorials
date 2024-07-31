package com.baeldung.recordswithjpa.records;

public record BookRecord(Long id, String title, String author, String isbn) {
}