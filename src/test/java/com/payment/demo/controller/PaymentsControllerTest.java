package com.payment.demo.controller;

import com.google.gson.Gson;
import com.payment.demo.exception.PaymentException;
import com.payment.demo.model.CurrencyType;
import com.payment.demo.model.PaymentDto;
import com.payment.demo.model.PaymentType;
import com.payment.demo.service.PaymentProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
class PaymentsControllerTest {
    @InjectMocks
    private PaymentsController controller;
    @Mock
    private PaymentProcessor processor;

    public MockMvc mockMvc;
    public Gson gson = new Gson();

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Mockito.when(processor.find( any(), any())).thenReturn(Collections.emptyList());
    }

    @Test
    public void makeType1PaymentWhenStatus201Created() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setAmount(BigDecimal.valueOf(0.01));
        request.setType(PaymentType.TYPE1.toString());
        request.setCurrency(CurrencyType.EUR.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");
        request.setDetails("Some details");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).proceed(any(PaymentDto.class));
    }

    @Test
    public void makeType2PaymentWhenStatus201Created() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setAmount(BigDecimal.valueOf(0.01));
        request.setType(PaymentType.TYPE2.toString());
        request.setCurrency(CurrencyType.USD.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");
        request.setDetails(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).proceed(any(PaymentDto.class));
    }

    @Test
    public void makeType3PaymentWhenStatus201Created() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setAmount(BigDecimal.valueOf(0.01));
        request.setType(PaymentType.TYPE3.toString());
        request.setCurrency(CurrencyType.USD.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");
        request.setBic("very_correct_bic");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).proceed(any(PaymentDto.class));
    }

    @Test
    public void cancelPaymentWhenStatus200OK() throws Exception {
        CancelPaymentDto request = new CancelPaymentDto("some_uuid", PaymentType.TYPE3.toString());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/payment/cancel")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).cancel(eq(request.getPaymentId()));
    }

    @Test
    public void cancelPaymentWhenStatus400BAD_REQUEST() throws Exception {
        CancelPaymentDto request = new CancelPaymentDto("some_not_existing_uuid", PaymentType.TYPE3.toString());

        doThrow(new PaymentException("some msg")).when(processor).cancel(eq(request.getPaymentId()));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/payment/cancel")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).cancel(eq(request.getPaymentId()));
    }

    @Test
    public void queryPaymentsThenStatus200_OK() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).find( eq(null), eq(null));
    }

    @Test
    public void queryPaymentByAmountFromThenStatus200_OK() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/payment?amountFrom=100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1))
                .find( eq(BigDecimal.valueOf(100)), eq(null));
    }

    @Test
    public void queryPaymentByAmountToThenStatus200_OK() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/payment?amountTo=100")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

         Mockito.verify(processor, Mockito.times(1))
                .find( eq(null), eq(BigDecimal.valueOf(100)));
    }
}
