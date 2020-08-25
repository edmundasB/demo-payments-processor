package com.demo.payment.controller;

import com.demo.payment.model.PaymentDto;
import com.demo.payment.model.PaymentEntity;
import com.demo.payment.model.PaymentStatus;
import com.demo.payment.service.PaymentProcessor;
import com.demo.payment.service.ipresolver.IPResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/v1/payment")
@Validated
public class PaymentsController {
    private final PaymentProcessor processor;
    private final IPResolver ipResolver;

    public PaymentsController(PaymentProcessor processor, IPResolver ipResolver) {
        this.processor = processor;
        this.ipResolver = ipResolver;
    }

    @PostMapping
    public ResponseEntity<String> create(@Valid @RequestBody PaymentDto paymentDto,
                                         HttpServletRequest request){
        try {
            processor.proceed(paymentDto);
            ipResolver.logCountryByIp(request.getRemoteAddr());
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{paymentId}/cancel")
    public ResponseEntity<String> cancel(@PathVariable String paymentId,
                                         HttpServletRequest request){
        try {
            processor.cancel(paymentId);
            ipResolver.logCountryByIp(request.getRemoteAddr());
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PaymentEntity>> query(@RequestParam(required = false) BigDecimal amountFrom,
                                                     @RequestParam(required = false) BigDecimal amountTo,
                                                     HttpServletRequest request){
        try {
            ipResolver.logCountryByIp(request.getRemoteAddr());
            return new ResponseEntity(processor.find(amountFrom, amountTo, PaymentStatus.PROCEED.toString()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{paymentId}/cancel")
    public ResponseEntity<List<PaymentEntity>> getCanceled(@PathVariable String paymentId,
                                                           HttpServletRequest request){
        try {
            ipResolver.logCountryByIp(request.getRemoteAddr());
            return new ResponseEntity(processor.find(paymentId, PaymentStatus.CANCELED.toString()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
