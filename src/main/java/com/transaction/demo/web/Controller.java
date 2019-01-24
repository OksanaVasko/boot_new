package com.transaction.demo.web;

import com.transaction.demo.dto.PaymentDTO;
import com.transaction.demo.dto.ResultDTO;
import com.transaction.demo.service.PaymentService;
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
        LocalDateTime timeNow = LocalDateTime.now();
        if (paymentDTO.getTimestamp().isAfter(timeNow)) {
            return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (paymentDTO.getTimestamp().plusSeconds(60).isBefore(timeNow)) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
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