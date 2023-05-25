package com.baeldung.java14.recordvslombok;

public record StudentRecord(String firstName, String lastName, Long studentId, String email, String phoneNumber, String address, String country, int age) {
}
