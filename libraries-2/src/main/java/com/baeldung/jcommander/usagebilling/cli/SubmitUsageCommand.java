package com.baeldung.jcommander.usagebilling.cli;

import static com.baeldung.jcommander.usagebilling.service.SubmitUsageService.getDefault;

import com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling.Constants;
import com.baeldung.jcommander.usagebilling.cli.converter.ISO8601TimestampConverter;
import com.baeldung.jcommander.usagebilling.model.UsageRequest;
import com.baeldung.jcommander.usagebilling.model.UsageRequest.PricingType;
import com.baeldung.jcommander.usagebilling.service.SubmitUsageService;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.BigDecimalConverter;
import java.math.BigDecimal;
import java.time.Instant;

@Parameters(commandNames = {Constants.SUBMIT_CMD})
class SubmitUsageCommand {

  SubmitUsageCommand() {
  }

  private SubmitUsageService service = getDefault();

  @Parameter(names = {"--customer", "-C"}, required = true)
  private String customerId;

  @Parameter(names = {"--subscription", "-S"}, required = true)
  private String subscriptionId;

  @Parameter(names = {"--pricing-type", "-P"}, required = true)
  private PricingType pricingType;

  @Parameter(names = {"--quantity"}, required = true)
  private Integer quantity;

  @Parameter(names = {"--timestamp"}, required = true, converter = ISO8601TimestampConverter.class)
  private Instant timestamp;

  @Parameter(names = {"--price"}, converter = BigDecimalConverter.class)
  private BigDecimal price;

  void submit() {
    UsageRequest req = UsageRequest.builder()
        .customerId(this.customerId)
        .subscriptionId(this.subscriptionId)
        .pricingType(this.pricingType)
        .quantity(this.quantity)
        .timestamp(this.timestamp)
        .price(this.price)
        .build();
    
    String reqId = service.submit(req);
    System.out.println("Generated Request Id for reference: " + reqId);
  }
}
