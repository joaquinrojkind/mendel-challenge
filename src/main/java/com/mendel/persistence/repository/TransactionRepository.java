package com.mendel.persistence.repository;

import com.mendel.persistence.entity.TransactionEntity;

import java.util.List;

public interface TransactionRepository {

    TransactionEntity updateTransaction(TransactionEntity transaction);

    List<TransactionEntity> getTransactionsByType(String type);

    List<TransactionEntity> findChildTransactionsByParentId(long transaction_id);

    TransactionEntity findTransactionById(long transaction_id);
}
