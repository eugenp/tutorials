package com.baeldung.jcommander.usagebilling.cli;

import com.baeldung.jcommander.usagebilling.cli.splitter.ColonParameterSplitter;
import com.baeldung.jcommander.usagebilling.cli.validator.UUIDValidator;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesRequest;
import com.baeldung.jcommander.usagebilling.model.CurrentChargesResponse;
import com.baeldung.jcommander.usagebilling.service.FetchCurrentChargesService;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;

import java.util.List;

import static com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling.*;
import static com.baeldung.jcommander.usagebilling.service.FetchCurrentChargesService.getDefault;

@Parameters(
  commandNames = { FETCH_CMD },
  commandDescription = "Fetch charges for a customer in the current month, can be itemized or aggregated"
)
@Getter
class FetchCurrentChargesCommand {

    FetchCurrentChargesCommand() {
    }

    private FetchCurrentChargesService service = getDefault();

    @Parameter(names = "--help", help = true)
    private boolean help;

    @Parameter(
      names = { "--customer", "-C" },
      description = "Id of the Customer who's using the services",
      validateWith = UUIDValidator.class,
      order = 1,
      required = true
    )
    private String customerId;

    @Parameter(
      names = { "--subscription", "-S" },
      description = "Filter charges for specific subscription Ids, includes all subscriptions if no value is specified",
      variableArity = true,
      splitter = ColonParameterSplitter.class,
      order = 2
    )
    private List<String> subscriptionIds;

    @Parameter(
      names = { "--itemized" },
      description = "Whether the response should contain breakdown by subscription, only aggregate values are returned by default",
      order = 3
    )
    private boolean itemized;

    void fetch() {
        CurrentChargesRequest req = CurrentChargesRequest.builder()
          .customerId(customerId)
          .subscriptionIds(subscriptionIds)
          .itemized(itemized)
          .build();

        CurrentChargesResponse response = service.fetch(req);
        System.out.println(response);
    }
}
