package com.example.demo;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class ResultDTO {

    private BigDecimal sum;

    private BigDecimal avg;

    private BigDecimal max;

    private BigDecimal min;

    private Long count;

    public ResultDTO(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, Long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public ResultDTO() {
    }
}
