package com.transaction.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
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
