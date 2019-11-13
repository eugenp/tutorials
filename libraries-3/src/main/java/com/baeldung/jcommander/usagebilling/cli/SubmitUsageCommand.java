package com.baeldung.jcommander.usagebilling.cli;

import com.baeldung.jcommander.usagebilling.cli.converter.ISO8601TimestampConverter;
import com.baeldung.jcommander.usagebilling.cli.validator.UUIDValidator;
import com.baeldung.jcommander.usagebilling.model.UsageRequest;
import com.baeldung.jcommander.usagebilling.model.UsageRequest.PricingType;
import com.baeldung.jcommander.usagebilling.service.SubmitUsageService;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

import static com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling.*;
import static com.baeldung.jcommander.usagebilling.service.SubmitUsageService.getDefault;

@Parameters(
  commandNames = { SUBMIT_CMD },
  commandDescription = "Submit usage for a given customer and subscription, accepts one usage item"
)
@Getter
class SubmitUsageCommand {

    SubmitUsageCommand() {
    }

    private SubmitUsageService service = getDefault();

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
      description = "Id of the Subscription that was purchased",
      order = 2,
      required = true
    )
    private String subscriptionId;

    @Parameter(
      names = { "--pricing-type", "-P" },
      description = "Pricing type of the usage reported",
      order = 3,
      required = true
    )
    private PricingType pricingType;

    @Parameter(
      names = { "--quantity" },
      description = "Used quantity; reported quantity is added over the billing period",
      order = 3,
      required = true
    )
    private Integer quantity;

    @Parameter(
      names = { "--timestamp" },
      description = "Timestamp of the usage event, must lie in the current billing period",
      converter = ISO8601TimestampConverter.class,
      order = 4,
      required = true
    )
    private Instant timestamp;

    @Parameter(
      names = { "--price" },
      description = "If PRE_RATED, unit price to be applied per unit of usage quantity reported",
      order = 5
    )
    private BigDecimal price;

    void submit() {
        
        UsageRequest req = UsageRequest.builder()
          .customerId(customerId)
          .subscriptionId(subscriptionId)
          .pricingType(pricingType)
          .quantity(quantity)
          .timestamp(timestamp)
          .price(price)
          .build();

        String reqId = service.submit(req);
        System.out.println("Generated Request Id for reference: " + reqId);
    }
}
