package com.baeldung.dddhexagonalapp.cli.adaptor;

import java.util.Scanner;

public interface CmdLineInterface {

    public void list(Scanner scanner);

    public void register(Scanner scanner);

    public void upgrade(Scanner scanner);

    public void downgrade(Scanner scanner);

    public void info();

}
