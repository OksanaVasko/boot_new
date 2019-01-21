package com.transaction.demo.service;

import com.transaction.demo.dto.ResultDTO;
import com.transaction.demo.model.Payment;
import com.transaction.demo.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;


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

    public ResultDTO getPaymensForLastOneMinute() {
        LocalDateTime oneMinuteBeforeNow = LocalDateTime.now().minusHours(2).minusMinutes(1);
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
        Payment minPayment = payments.stream().min(Comparator.comparing(Payment::getAmount)).orElseThrow(NoSuchElementException::new);
        Payment maxPayment = payments.stream().max(Comparator.comparing(Payment::getAmount)).orElseThrow(NoSuchElementException::new);
        BigDecimal sum = payments.stream().map(Payment::getAmount).reduce(BigDecimal::add).get();
        BigDecimal average = sum.divide(BigDecimal.valueOf(payments.size()), 2, BigDecimal.ROUND_HALF_UP);
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setAvg(average);
        resultDTO.setCount((long) payments.size());
        resultDTO.setMax(maxPayment.getAmount());
        resultDTO.setMin(minPayment.getAmount());
        resultDTO.setSum(sum);
        return resultDTO;
    }

}