package com.payment.demo.service;

import com.payment.demo.exception.PaymentException;
import com.payment.demo.model.PaymentDto;
import com.payment.demo.model.PaymentEntity;
import com.payment.demo.model.PaymentStatus;
import com.payment.demo.model.PaymentType;
import com.payment.demo.repository.PaymentRepository;
import com.payment.demo.service.fee.FeeCalculator;
import com.payment.demo.service.fee.FeeCalculatorFactory;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PaymentProcessorTest {
    public static final String EXISTING_PAYMENT_ID = "some-uuid1";
    public static final String NOT_EXISTING_PAYMENT_ID = "some-not-existing-uuid";
    @InjectMocks
    @Spy
    private PaymentProcessorImpl processor;
    @Mock
    private PaymentRepository repository;
    @Mock
    private FeeCalculatorFactory feeCalculatorFactory;
    @Mock
    private FeeCalculator feeCalculator;

    @Captor
    private ArgumentCaptor<PaymentEntity> paymentCaptor;

    private LocalDateTime currentDateTime = LocalDateTime.of(2020, 8, 22, 12, 28);

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.when(processor.getCurrentDateTime()).thenReturn(currentDateTime);

        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setType(PaymentType.TYPE1.toString());

        PaymentEntity payment= new PaymentEntity(paymentDto);
        payment.setCreateDate(currentDateTime.minusHours(3));

        Mockito.when(repository.findById(eq(EXISTING_PAYMENT_ID))).thenReturn(Optional.of( payment ));

        Mockito.when(feeCalculatorFactory.getFeeCalculator(any())).thenReturn(feeCalculator);

        Mockito.when(repository.findById(eq(NOT_EXISTING_PAYMENT_ID))).thenReturn(Optional.empty() );

    }

    @Test
    public void proceedPayment() {
        processor.proceed(new PaymentDto());
        verify(repository, times(1)).save(any(PaymentEntity.class));
    }

    @Test
    public void cancelPayment() throws Exception {
        processor.cancel(EXISTING_PAYMENT_ID);
        verify(repository, times(1)).save(paymentCaptor.capture());
        Assert.assertEquals(PaymentStatus.CANCELED.toString(), paymentCaptor.getValue().getStatus());

        verify(feeCalculator, times(1)).calculateCancellationFee(any(LocalDateTime.class));
    }

    @Test
    public void cancelNotExistingPaymentWhenException(){
        Assertions.assertThrows(PaymentException.class, () -> { processor.cancel(NOT_EXISTING_PAYMENT_ID); } );
    }

    @Test
    public void cancelPaymentAnotherDayAfterCreationWhenException(){
        String yesterdayPaymentId = "very_yesterday_payment_id";
        PaymentEntity payment = new PaymentEntity(new PaymentDto());

        payment.setCreateDate(currentDateTime.minus(1, ChronoUnit.DAYS));

        Mockito.when(repository.findById(eq(yesterdayPaymentId))).thenReturn(Optional.of( payment ));
        Assertions.assertThrows(PaymentException.class, () -> { processor.cancel( yesterdayPaymentId ); } );
    }

}
