package com.transaction.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class PaymentDTO {

    @NotNull
    private BigDecimal amount;

    private LocalDateTime timestamp;

    public PaymentDTO(BigDecimal amount, LocalDateTime timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public PaymentDTO() {

    }
}
