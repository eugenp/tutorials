package com.baeldung.patterns.commands;

import javax.servlet.ServletException;
import java.io.IOException;

public class UnknownCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        super.process();
        forward("unknown");
    }
}
