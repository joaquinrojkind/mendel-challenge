package com.mendel.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Transaction {

    private Long transactionId;
    private Double amount;
    private String type;
    private Long parentId;
}
