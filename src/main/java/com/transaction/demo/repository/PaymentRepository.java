package com.transaction.demo.repository;

import com.transaction.demo.model.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.timeStamp>=:minuteBefore ")
    List<Payment> findAllPaymentsInLastOneMinute(@Param("minuteBefore") LocalDateTime minuteBefore);
}
