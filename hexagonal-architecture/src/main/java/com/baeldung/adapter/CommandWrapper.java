package com.baeldung.adapter;

import com.baeldung.port.CommandHandler;
import io.vertx.core.json.JsonObject;

public class CommandWrapper {

    public static CommandHandler createCustomerAccount(JsonObject jo) {
        return new CreateCustomerCommandHandler(jo);
    }
}
