package com.mendel.persistence.entity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionEntity {

    private long transaction_id;
    private Double amount;
    private String type;
    private long parent_id;
}
