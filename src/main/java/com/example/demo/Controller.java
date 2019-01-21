package com.example.demo;

import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class Controller {

    @Autowired
    private PaymentService paymentService;

    @RequestMapping(value = "/statistics", method = GET)
    public @ResponseBody
    ResultDTO getPayments() {
        return paymentService.getPaymensForLastOneMinute();
    }

    @RequestMapping(path = "/transactions", method = POST)
    public ResponseEntity addPayment(@RequestBody @Validated PaymentDTO paymentDTO) {
        if (paymentDTO.getTimestamp().isAfter(LocalDateTime.now())) {
            throw new BadTransactionException();
        }
        paymentService.addPayment(paymentDTO.getAmount(), paymentDTO.getTimestamp());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/transactions", method = DELETE)
    public ResponseEntity deleteAllPayments() {
        paymentService.deletePayments();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}