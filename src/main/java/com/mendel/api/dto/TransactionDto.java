package com.mendel.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TransactionDto {

    private long transaction_id;
    private Double amount;
    private String type;
    private long parent_id;
}
