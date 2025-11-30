package com.baeldung.temporal.workflows.sboot.order.domain;

public record ShippingInfo(
  String shipTo,
  String addrLine1,
  String addrLine2,
  String postalCode,
  String city,
  String stateOrProvince,
  String countryCode,
  String contactPhone,
  String contactEmail,
  String contactName,
  String deliveryInstructions
) {
}
