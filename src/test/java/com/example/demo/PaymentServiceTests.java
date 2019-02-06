package com.example.demo;

import com.transaction.demo.dto.ResultDTO;
import com.transaction.demo.model.Payment;
import com.transaction.demo.repository.PaymentRepository;
import com.transaction.demo.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTests {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    public void get_paymens_for_last_one_minute_test() {
        LocalDateTime timeStamp = LocalDateTime.now();
        List<Payment> payments = createPaymentsList(timeStamp);
        when(paymentRepository.findAllPaymentsInLastOneMinute(timeStamp)).thenReturn(payments);
        ResultDTO resultDTOCurrent = paymentService.getPaymensForLastOneMinute(timeStamp);
        ResultDTO resultDTOExpected = new ResultDTO(new BigDecimal("150.00"), new BigDecimal("75.00"), new BigDecimal("80.00"), new BigDecimal("70.00"), new Long(2));
        assertEquals(resultDTOCurrent, resultDTOExpected);

    }


    @Test
    public void add_payments_test() {
        LocalDateTime timeStamp = LocalDateTime.now();
        Payment p = new Payment(new BigDecimal(80), timeStamp);
        paymentService.addPayment(new BigDecimal(80), timeStamp);
        verify(paymentRepository, times(1)).save(p);

    }

    @Test
    public void delete_payments_test() {
        LocalDateTime timeStamp = LocalDateTime.now();
        List<Payment> payments = createPaymentsList(timeStamp);
        paymentRepository.saveAll(payments);
        paymentService.deletePayments();
        verify(paymentRepository, times(1)).deleteAll();

    }

    private List<Payment> createPaymentsList(LocalDateTime timeStamp) {
        Payment payment1 = new Payment(new BigDecimal(80), timeStamp);
        Payment payment2 = new Payment(new BigDecimal(70), timeStamp);
        List<Payment> payments = new ArrayList<>();
        payments.add(payment1);
        payments.add(payment2);
        return payments;
    }


}
