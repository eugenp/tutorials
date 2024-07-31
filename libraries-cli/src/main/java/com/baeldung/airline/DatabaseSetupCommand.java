package com.baeldung.airline;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.github.rvesse.airline.HelpOption;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import com.github.rvesse.airline.annotations.OptionType;
import com.github.rvesse.airline.annotations.restrictions.AllowedRawValues;
import com.github.rvesse.airline.annotations.restrictions.MutuallyExclusiveWith;
import com.github.rvesse.airline.annotations.restrictions.Pattern;
import com.github.rvesse.airline.annotations.restrictions.RequiredOnlyIf;

@Command(name = "setup-db", description = "Setup our database")
public class DatabaseSetupCommand implements Runnable {
    @Inject
    private HelpOption<DatabaseSetupCommand> help;

    @Option(type = OptionType.COMMAND,
      name = {"-d", "--database"},
      description = "Type of RDBMS.",
      title = "RDBMS type: mysql|postgresql|mongodb")
    @AllowedRawValues(allowedValues = { "mysql", "postgres", "mongodb" })
    protected String rdbmsMode = "mysql";

    @Option(type = OptionType.COMMAND,
      name = {"--rdbms:url", "--url"},
      description = "URL to use for connection to RDBMS.",
      title = "RDBMS URL")
    @MutuallyExclusiveWith(tag="mode")
    @Pattern(pattern="^(http://.*):(d*)(.*)u=(.*)&p=(.*)")
    protected String rdbmsUrl = "";

    @Option(type = OptionType.COMMAND,
      name = {"--rdbms:host", "--host"},
      description = "Host to use for connection to RDBMS.",
      title = "RDBMS host")
    @MutuallyExclusiveWith(tag="mode")
    protected String rdbmsHost = "";

    @RequiredOnlyIf(names={"--rdbms:host", "--host"})
    @Option(type = OptionType.COMMAND,
      name = {"--rdbms:user", "-u", "--user"},
      description = "User for login to RDBMS.",
      title = "RDBMS user")
    protected String rdbmsUser;

    @RequiredOnlyIf(names={"--rdbms:host", "--host"})
    @Option(type = OptionType.COMMAND,
      name = {"--rdbms:password", "--password"},
      description = "Password for login to RDBMS.",
      title = "RDBMS password")
    protected String rdbmsPassword;

    @Option(type = OptionType.COMMAND,
      name = {"--driver", "--jars"},
      description = "List of drivers",
      title = "--driver <PATH_TO_YOUR_JAR> --driver <PATH_TO_YOUR_JAR>")
    protected List<String> jars = new ArrayList<>();

    @Override
    public void run() {
        //skipping store our choices...
        if (!help.showHelpIfRequested()) {
            if(!"".equals(rdbmsHost)) {
                System.out.println("Connecting to database host: " + rdbmsHost);
                System.out.println("Credential: " + rdbmsUser + " / " + rdbmsPassword);
            } else {
                System.out.println("Connecting to database url: " + rdbmsUrl);
            }
            System.out.println(jars.toString());
        }
    }
}
