package com.baeldung.jcommander.usagebilling.cli;

import com.baeldung.jcommander.usagebilling.cli.UsageBasedBilling.Constants;
import com.baeldung.jcommander.usagebilling.cli.converter.ISO8601TimestampConverter;
import com.baeldung.jcommander.usagebilling.model.UsageRequest.PricingType;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.beust.jcommander.converters.BigDecimalConverter;
import java.math.BigDecimal;
import java.time.Instant;

@Parameters(commandNames = {Constants.CREATE_CMD})
class SubmitUsageCommand {

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

  SubmitUsageCommand() {
  }

  void create() {

  }
}
