package com.baeldung.hexagonal.adapter.console;

import java.util.Scanner;

public interface CommandInterface {

    public void list(Scanner scanner);

    public void register(Scanner scanner);

    public void upgrade(Scanner scanner);

    public void downgrade(Scanner scanner);

    public void info();

}
