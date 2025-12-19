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

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionDto> updateTransaction(@PathVariable("transactionId") Long transactionId, @RequestBody TransactionDto transactionDto) {
        transactionDto.setTransactionId(transactionId);
        Transaction transaction = transactionService.updateTransaction(toTransaction(transactionDto));
        return ResponseEntity.ok().body(toTransactionDto(transaction));
    }

    @GetMapping("/types/{type}")
    public List<Long> getByType(@PathVariable("type") String type) {
        return transactionService.getTransactionIdsByType(type);
    }

    @GetMapping("/sum/{transactionId}")
    public ResponseEntity<SumTransactionsResponseDto> sumTransactions(@PathVariable("transactionId") Long transactionId) {
        double sum = transactionService.sumTransactions(transactionId);
        return ResponseEntity.ok(
                SumTransactionsResponseDto.builder()
                        .sum(sum)
                        .build());
    }

    private Transaction toTransaction(TransactionDto transactionDto) {
        return Transaction.builder()
                .transactionId(transactionDto.getTransactionId())
                .amount(transactionDto.getAmount())
                .type(transactionDto.getType())
                .parentId(transactionDto.getParentId())
                .build();
    }

    private TransactionDto toTransactionDto(Transaction transaction) {
        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .parentId(transaction.getParentId())
                .build();
    }
}
