package com.account.service.api.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class AccountsResponse {
  List<AccountResponse> accounts;
}
