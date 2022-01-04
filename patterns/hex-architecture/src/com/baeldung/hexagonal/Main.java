package com.baeldung.hexagonal;

import com.baeldung.hexagonal.adapter.MobileLoginAdapter;
import com.baeldung.hexagonal.adapter.MongoDatabaseAdapter;
import com.baeldung.hexagonal.adapter.PostgresDatabaseAdapter;
import com.baeldung.hexagonal.adapter.WebLoginAdapter;
import com.baeldung.hexagonal.port.UserDatabasePort;
import com.baeldung.hexagonal.service.UserService;

public class Main {

    public static void main(String[] args) {

        UserDatabasePort mongoDatabase = new MongoDatabaseAdapter();
        UserService mongoService = new UserService(mongoDatabase);
        WebLoginAdapter webLoginAdapter = new WebLoginAdapter(mongoService);
        System.out.println(webLoginAdapter.getLoginToken("Web", "password"));

        UserDatabasePort postgresDatabase = new PostgresDatabaseAdapter();
        UserService postgresService = new UserService(postgresDatabase);
        MobileLoginAdapter mobileLoginAdapter = new MobileLoginAdapter(postgresService);
        System.out.println(mobileLoginAdapter.getLoginToken("Mobile", "password"));
    }
}