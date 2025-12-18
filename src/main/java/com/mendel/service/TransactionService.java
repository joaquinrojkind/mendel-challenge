package com.mendel.service;

import com.mendel.service.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction updateTransaction(Transaction transaction);

    List<Long> getTransactionIdsByType(String type);

    double sumTransactions(long transaction_id);
}
