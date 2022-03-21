package com.baeldung.hexagonal.architecture.example.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.example.model.Transaction;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionsAssembler {

	public TransactionResponse toModel(Transaction entity) {
		TransactionResponse dto = new TransactionResponse();
		dto.setAmount(entity.getAmount());
		dto.setReferenceNo(entity.getReferenceNo());
		dto.setSourceAccountNo(entity.getSourceAccount()
			.getId());
		dto.setTargetAccountNo(entity.getTargetAccount()
			.getId());
		dto.setDetails(entity.getDetails());
		return dto;
	}

	public List<TransactionResponse> toModel(List<Transaction> entities) {
		if (entities == null) {
			return null;
		}

		List<TransactionResponse> list = new ArrayList<TransactionResponse>(entities.size());
		for (Transaction transaction : entities) {
			list.add(toModel(transaction));
		}
		return list;
	}
}