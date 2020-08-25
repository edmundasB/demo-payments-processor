package com.demo.payment.controller;

import com.demo.payment.model.PaymentStatus;
import com.demo.payment.service.ipresolver.IPResolver;
import com.google.gson.Gson;
import com.demo.payment.exception.PaymentException;
import com.demo.payment.model.CurrencyType;
import com.demo.payment.model.PaymentDto;
import com.demo.payment.model.PaymentType;
import com.demo.payment.service.PaymentProcessor;
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
    @Mock
    private IPResolver ipResolver;

    public MockMvc mockMvc;
    public Gson gson = new Gson();

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        Mockito.when(processor.find( any(), any(), any())).thenReturn(Collections.emptyList());
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
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/payment/some-id/cancel")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).cancel(eq("some-id"));
    }

    @Test
    public void cancelPaymentWhenStatus400BAD_REQUEST() throws Exception {
        doThrow(new PaymentException("some msg")).when(processor).cancel(eq("some_not_existing_uuid"));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .put("/v1/payment/some_not_existing_uuid/cancel")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1)).cancel(eq("some_not_existing_uuid"));
    }

    @Test
    public void queryPaymentsThenStatus200_OK() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Mockito.verify(processor, Mockito.times(1))
                .find( eq(null), eq(null), eq(PaymentStatus.PROCEED.toString()));
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
                .find( eq(BigDecimal.valueOf(100)), eq(null), eq(PaymentStatus.PROCEED.toString()));
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
                .find( eq(null), eq(BigDecimal.valueOf(100)), eq(PaymentStatus.PROCEED.toString()) );
    }
}
