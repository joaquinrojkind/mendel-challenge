package com.mendel.persistence.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class TransactionEntity {

    private Long transactionId;
    private Double amount;
    private String type;
    private Long parentId;
}
