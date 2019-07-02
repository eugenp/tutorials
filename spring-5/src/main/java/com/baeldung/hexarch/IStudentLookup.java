package com.baeldung.hexarch;

@FunctionalInterface
public interface IStudentLookup {
    Student find(int id);
}
