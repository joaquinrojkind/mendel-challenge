package com.mendel.persistence.repository;

import com.mendel.persistence.entity.TransactionEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

	// database emulation
	Map<Long, TransactionEntity> DATABASE = new HashMap<>();

	@Override
	public TransactionEntity updateTransaction(TransactionEntity transaction) {
		DATABASE.put(transaction.getTransactionId(), transaction);

		printDatabaseState("updateTransaction");

		return transaction;
	}

	@Override
	public List<TransactionEntity> getTransactionsByType(String type) {

		printDatabaseState("getTransactionsByType");

		return DATABASE.values().stream()
				.filter(transactionEntity -> transactionEntity.getType().equals(type))
				.collect(Collectors.toList());

	}

	@Override
	public List<TransactionEntity> findChildTransactionsByParentId(Long transactionId) {

		printDatabaseState("findChildTransactionsByParentId");

		List<TransactionEntity> transactionEntities = DATABASE.values().stream()
				.filter(transactionEntity -> transactionEntity.getParentId() != null && transactionEntity.getParentId() == transactionId)
				.collect(Collectors.toList());
		return transactionEntities;
	}

	@Override
	public TransactionEntity findTransactionById(Long transactionId) {

		printDatabaseState("findTransactionById");

		return DATABASE.get(transactionId);
	}

	/*
	  For debugging and testing purposes since the persistence layer is mocked
	 */
	private void printDatabaseState(String operation) {
		log.info("Executing DATABASE operation {}", operation);
		log.info("Printing resulting database state...");
		DATABASE.forEach((k, v) -> log.info("Key: {}, Value: {}", k, v));
	}
}
