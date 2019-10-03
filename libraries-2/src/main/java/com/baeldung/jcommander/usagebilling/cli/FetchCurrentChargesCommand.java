package com.baeldung.jcommander.usagebilling.cli;

import static com.baeldung.jcommander.usagebilling.service.FetchCurrentChargesService.getDefault;

import com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling.Constants;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesRequest;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesResponse;
import com.baeldung.jcommander.usagebilling.service.FetchCurrentChargesService;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import java.util.List;

@Parameters(commandNames = {Constants.FETCH_CMD})
class FetchCurrentChargesCommand {

  FetchCurrentChargesCommand() {
  }

  private FetchCurrentChargesService service = getDefault();

  @Parameter(names = {"--customer", "-C"}, required = true)
  private String customerId;

  @Parameter(names = {"--subscription", "-S"}, variableArity = true)
  private List<String> subscriptionIds;

  void fetch() {
    CurrentChargesRequest req = CurrentChargesRequest.builder()
        .customerId(this.customerId)
        .subscriptionIds(this.subscriptionIds)
        .build();

    CurrentChargesResponse response = service.fetch(req);
    System.out.println("Current charges:\n" + response);
  }
}
