package com.mendel.service.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Transaction {

    private long transaction_id;
    private Double amount;
    private String type;
    private long parent_id;
}
