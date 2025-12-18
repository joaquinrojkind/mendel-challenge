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

		printDatabaseState("updateTransaction [before]");

		DATABASE.put(transaction.getTransactionId(), transaction);

		printDatabaseState("updateTransaction [after]");

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
	public List<TransactionEntity> findChildTransactionsByParentId(long transaction_id) {

		printDatabaseState("findChildTransactionsByParentId");

		List<TransactionEntity> transactionEntities = DATABASE.values().stream()
				.filter(transactionEntity -> transactionEntity.getParent_id() == transaction_id)
				.collect(Collectors.toList());
		return transactionEntities;
	}

	@Override
	public TransactionEntity findTransactionById(long transaction_id) {

		printDatabaseState("findTransactionById");

		return DATABASE.get(transaction_id);
	}

	/*
	  For debugging and testing purposes since the persistence layer is mocked
	 */
	private void printDatabaseState(String operation) {
		log.info("Executing DATABASE operation {}", operation);
		log.info("Printing current database state...");
		DATABASE.forEach((k, v) -> log.info("Key: {}, Value: {}", k, v));
	}
}
