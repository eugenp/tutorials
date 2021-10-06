package com.account.service.repository;

import com.account.service.domain.Transfer;
import com.account.service.repository.command.InsertTransferCommand;
import com.account.service.repository.jpa.TransferJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.account.service.repository.RepositoryConverter.toTransfer;
import static com.account.service.repository.RepositoryConverter.toTransferEntity;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TransferRepositoryImpl implements TransferRepository {

  private final TransferJpaRepository moneyTransferJpaRepository;

  @Override
  public Transfer save(final InsertTransferCommand command) {
    log.info("Save money transfer with command : {}", command);
    return toTransfer(moneyTransferJpaRepository.save(toTransferEntity(command)));
  }

}
