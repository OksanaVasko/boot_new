package com.transaction.demo.service;

import com.transaction.demo.dto.ResultDTO;
import com.transaction.demo.model.Payment;
import com.transaction.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;


@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public void addPayment(BigDecimal amount, LocalDateTime timestamp) {
        paymentRepository.save(new Payment(amount, timestamp));
    }

    public void deletePayments() {
        paymentRepository.deleteAll();
    }

    public ResultDTO getPaymensForLastOneMinute(LocalDateTime oneMinuteBeforeNow) {
        List<Payment> payments = paymentRepository.findAllPaymentsInLastOneMinute(oneMinuteBeforeNow);
        ResultDTO resultDTO;
        if (payments.isEmpty()) {
            resultDTO = new ResultDTO(new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new Long("0"));
        } else {
            resultDTO = processResult(payments);
        }
        return resultDTO;
    }

    private ResultDTO processResult(List<Payment> payments) {
        DoubleSummaryStatistics statistics = payments.stream().map(Payment::getAmount).mapToDouble(BigDecimal::doubleValue).summaryStatistics();
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCount(statistics.getCount());
        resultDTO.setAvg(BigDecimal.valueOf(statistics.getAverage()).setScale(2, RoundingMode.HALF_UP));
        resultDTO.setMax(BigDecimal.valueOf(statistics.getMax()).setScale(2, RoundingMode.HALF_UP));
        resultDTO.setMin(BigDecimal.valueOf(statistics.getMin()).setScale(2, RoundingMode.HALF_UP));
        resultDTO.setSum(BigDecimal.valueOf(statistics.getSum()).setScale(2, RoundingMode.HALF_UP));
        return resultDTO;
    }

}