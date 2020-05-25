package com.baeldung.hexagonal.app;

import com.baeldung.hexagonal.adapter.JavaClientAdapter;

public class ClientApplication {

    public static void main(String[] args) {
        JavaClientAdapter inputAdapter = new JavaClientAdapter();
        JavaClientAdapter.Param param = new JavaClientAdapter.Param();
        param.setTodoName("Running for 20 minutes.");
        inputAdapter.createNewToDoItem(param);
    }
}
