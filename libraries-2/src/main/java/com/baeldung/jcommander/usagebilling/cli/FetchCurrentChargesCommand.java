package com.baeldung.jcommander.usagebilling.cli;

import com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling.Constants;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;

@Parameters(commandNames = {Constants.FETCH_CMD})
class FetchCurrentChargesCommand {

  @Parameter(names = {"--customer", "-C"}, required = true)
  private String customerId;

  @Parameter(names = {"--subscription", "-S"}, variableArity = true)
  private List<String> subscriptionIds;

  FetchCurrentChargesCommand() {
  }

  void fetch() {

  }
}
