package com.baeldung.mapper;

import com.baeldung.dto.TransactionDTO;
import com.baeldung.entity.Transaction;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Mapper
interface TransactionMapper {

    default TransactionDTO toTransactionDTO(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUuid(transaction.getUuid());
        transactionDTO.setTotalInCents(transaction.getTotal().multiply(new BigDecimal("100")).longValue());
        return transactionDTO;
    }

    List<TransactionDTO> toTransactionDTO(Collection<Transaction> transactions);
}
