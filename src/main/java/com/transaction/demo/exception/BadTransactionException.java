package com.transaction.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY, reason = "Wrong timestamp - in future")
public class BadTransactionException extends RuntimeException {

}
