package com.baeldung.hexagonal.client;

import com.baeldung.hexagonal.adapter.clientapp.JavaClientAdapter;

public class ClientApplication {
    public static void main(String[] args) {
        JavaClientAdapter adapter = new JavaClientAdapter();
        adapter.createNewToDoItem("Running for 20 minutes.");
    }
}
