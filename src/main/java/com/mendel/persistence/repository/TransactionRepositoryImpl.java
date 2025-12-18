package com.mendel.persistence.repository;

import com.mendel.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

	// database emulation
	Map<Long, TransactionEntity> DATABASE;

	@Override
	public TransactionEntity updateTransaction(TransactionEntity transaction) {
		TransactionEntity entity = DATABASE.get(transaction.getTransaction_id());
		DATABASE.put(transaction.getTransaction_id(), transaction);
		return entity;
	}

	@Override
	public List<TransactionEntity> getTransactionsByType(String type) {
		return DATABASE.values().stream()
				.filter(transactionEntity -> transactionEntity.equals(transactionEntity.getType()))
				.collect(Collectors.toList());
	}

	@Override
	public List<TransactionEntity> findChildTransactionsByParentId(long transaction_id) {
		List<TransactionEntity> transactionEntities = DATABASE.values().stream()
				.filter(transactionEntity -> transactionEntity.getParent_id() == transaction_id)
				.collect(Collectors.toList());
		return transactionEntities;
	}

	@Override
	public TransactionEntity findTransactionById(long transaction_id) {
		return DATABASE.get(transaction_id);
	}
}
