package com.baeldung.jcommander.helloworld;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.CharArrayConverter;
import java.util.ArrayList;
import java.util.List;

public class HelloWorldExample {

  public static void main(String[] args) {

//    String[] argv = {"--debug"}; // {"--debug", "false"}
//    String[] argv = {"--host", "host1", "-h", "host2"};
    String[] argv = {"-p"};

    Args jArgs = new Args();
    JCommander.newBuilder().addObject(jArgs).build().parse(argv);

    System.out.println(jArgs.debug);
    System.out.println(jArgs.hosts);
    System.out.println(jArgs.password);
  }

  @Parameters
  static class Args {

    @Parameter(names = {"--debug", "-d"}, description = "Debug mode")//, arity = 1)
    private boolean debug = false;

    @Parameter(names = {"--host", "-h"}, description = "The host")
    private List<String> hosts = new ArrayList<>();

    @Parameter(names = {"--password", "-p"}, description = "Connection password",
        password = true, echoInput = true, converter = CharArrayConverter.class)
    private char[] password;
  }
}
