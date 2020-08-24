package com.payment.demo.controller;

import com.payment.demo.model.PaymentDto;
import com.payment.demo.model.PaymentEntity;
import com.payment.demo.service.PaymentProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/payment")
@Validated
public class PaymentsController {
    private final PaymentProcessor processor;

    public PaymentsController(PaymentProcessor processor) {
        this.processor = processor;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody PaymentDto request){
        try {
            processor.proceed(request);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/cancel")
    public ResponseEntity<String> cancel(@Valid @RequestBody CancelPaymentDto request){
        try {
            processor.cancel(request.getPaymentId());
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PaymentEntity>> query(@RequestParam(required = false) BigDecimal amountFrom,
                                                   @RequestParam(required = false) BigDecimal amountTo){
        try {
            return new ResponseEntity(processor.find(amountFrom, amountTo), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("{paymentId}")
    public ResponseEntity<List<PaymentEntity>> getByIds(@PathVariable String paymentId){
        try {
            return new ResponseEntity(processor.findById(paymentId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
