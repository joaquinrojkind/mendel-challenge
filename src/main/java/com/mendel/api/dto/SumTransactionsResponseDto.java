package com.mendel.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SumTransactionsResponseDto {

    private double sum;
}
