package com.demo.payment.validation;

import com.google.gson.Gson;
import com.demo.payment.controller.PaymentsController;
import com.demo.payment.model.CurrencyType;
import com.demo.payment.model.PaymentDto;
import com.demo.payment.model.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaymentValidationTest {

    @InjectMocks
    private PaymentsController controller;
    public MockMvc mockMvc;
    public Gson gson = new Gson();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void makeUnknownTypePaymentWhenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType("Unknown");
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");
        request.setCurrency(CurrencyType.EUR.toString());


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void makePaymentWithNegativeAmountThenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType(PaymentType.TYPE1.toString());
        request.setAmount(BigDecimal.valueOf(-1000));
        request.setCurrency(CurrencyType.EUR.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void makePaymentWithZeroAmountThenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType(PaymentType.TYPE1.toString());
        request.setAmount(BigDecimal.ZERO);
        request.setCurrency(CurrencyType.EUR.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void makePaymentWithUnknownCurrencyThenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType(PaymentType.TYPE1.toString());
        request.setAmount(BigDecimal.valueOf(1000));
        request.setCurrency("Unknown");
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void makePaymentWithoutDebtorIbanThenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType(PaymentType.TYPE1.toString());
        request.setAmount(BigDecimal.valueOf(1000));
        request.setCurrency(CurrencyType.EUR.toString());
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void makePaymentWithoutCreditorIbanThenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType(PaymentType.TYPE1.toString());
        request.setAmount(BigDecimal.valueOf(1000));
        request.setCurrency(CurrencyType.EUR.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void makeIncorrectType1PaymentThenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType(PaymentType.TYPE1.toString());
        request.setAmount(BigDecimal.valueOf(1000));
        request.setCurrency(CurrencyType.EUR.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");
        request.setDetails(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }

    @Test
    public void makeIncorrectType3PaymentThenBadRequest400() throws Exception {
        PaymentDto request = new PaymentDto();
        request.setType(PaymentType.TYPE3.toString());
        request.setAmount(BigDecimal.valueOf(1000));
        request.setCurrency(CurrencyType.USD.toString());
        request.setDebtorIban("LTSomeVeryCorrectIBAN");
        request.setCreditorIban("LTSomeVeryCorrectIBAN2");
        request.setDetails(null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/v1/payment")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
    }
}
