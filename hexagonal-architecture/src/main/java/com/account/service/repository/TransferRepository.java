package com.account.service.repository;

import com.account.service.domain.Transfer;
import com.account.service.repository.command.InsertTransferCommand;

public interface TransferRepository {
  Transfer save(final InsertTransferCommand command);
}
