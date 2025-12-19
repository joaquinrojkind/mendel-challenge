package com.mendel.service;

import com.mendel.persistence.entity.TransactionEntity;
import com.mendel.persistence.repository.TransactionRepository;
import com.mendel.service.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        TransactionEntity updatedTransaction = repository.updateTransaction(toTransactionEntity(transaction));
        return toTransaction(updatedTransaction);
    }

    @Override
    public List<Long> getTransactionIdsByType(String type) {
        List<TransactionEntity> matches = repository.getTransactionsByType(type);
        return matches.stream()
                .map(transaction -> transaction.getTransactionId())
                .collect(Collectors.toList());
    }

    @Override
    public double sumTransactions(Long transactionId) {
       TransactionEntity parentTransaction = repository.findTransactionById(transactionId);
       if (parentTransaction == null) {
           return 0.00;
       }
       List<TransactionEntity> childTransactionsByParentId = repository.findChildTransactionsByParentId(transactionId);

        List<Double> values = childTransactionsByParentId.stream()
                .filter(transactionEntity -> transactionEntity.getParentId() != null && transactionEntity.getParentId() == transactionId)
                .map(TransactionEntity::getAmount)
                .collect(Collectors.toList());

        Double sum = values.stream().reduce(0.00, Double::sum);

        return sum + parentTransaction.getAmount();
    }

    private TransactionEntity toTransactionEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parentId(transaction.getParentId())
                .build();
    }

    private Transaction toTransaction(TransactionEntity transaction) {
        return Transaction.builder()
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parentId(transaction.getParentId())
                .build();
    }
}

