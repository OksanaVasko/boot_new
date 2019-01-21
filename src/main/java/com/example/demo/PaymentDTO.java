package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
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
