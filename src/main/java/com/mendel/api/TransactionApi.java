package com.mendel.api;

import com.mendel.api.dto.SumTransactionsResponseDto;
import com.mendel.api.dto.TransactionDto;
import com.mendel.service.TransactionService;
import com.mendel.service.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionApi {

    @Autowired
    private TransactionService transactionService;

    @PutMapping("/{transaction_id}")
    public ResponseEntity<TransactionDto> editTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = transactionService.updateTransaction(toTransaction(transactionDto));
        return ResponseEntity.ok().body(toTransactionDto(transaction));
    }

    @GetMapping("/types/{type}")
    public List<Long> getTypes(@PathVariable("type") String type) {
        List<Long> ids = transactionService.getTransactionIdsByType(type);
        return ids;
    }

    @GetMapping("/sum/{transaction_id}")
    public ResponseEntity<SumTransactionsResponseDto> sumTransactions(@PathVariable("transaction_id") Long transaction_id) {

        double sum = transactionService.sumTransactions(transaction_id);

        return ResponseEntity.ok(
                SumTransactionsResponseDto.builder()
                        .sum(sum)
                        .build());
    }

    private Transaction toTransaction(TransactionDto transactionDto) {
        return Transaction.builder()
                .transaction_id(transactionDto.getTransaction_id())
                .amount(transactionDto.getAmount())
                .type(transactionDto.getType())
                .parent_id(transactionDto.getParent_id())
                .build();
    }

    private TransactionDto toTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .transaction_id(transaction.getTransaction_id())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parent_id(transaction.getParent_id())
                .build();
    }
}
