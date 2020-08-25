package com.demo.payment.service.ipresolver;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;


import static org.mockito.ArgumentMatchers.any;

class IPResolverTest {
    @InjectMocks
    private IPResolver resolver;
    @Mock
    private RestTemplate restTemplate;
    Logger logger = (Logger) LoggerFactory.getLogger(IPResolver.class);
    MemoryAppender memoryAppender = new MemoryAppender();

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        memoryAppender = new MemoryAppender();
        memoryAppender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.DEBUG);
        logger.addAppender(memoryAppender);
        memoryAppender.start();


        IpResolverResponse successResponse = new IpResolverResponse();
        successResponse.setCountry("Japan");
        successResponse.setStatus("success");

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.eq("http://ip-api.com/json/125.0.0.1?fields=status,country,message"),
                ArgumentMatchers.eq(HttpMethod.GET),
                any(),
                ArgumentMatchers.<Class<IpResolverResponse>>any())).thenReturn(ResponseEntity.ok(successResponse));

        IpResolverResponse failResponse = new IpResolverResponse();
        failResponse.setMessage("invalid query");
        failResponse.setStatus("fail");

        Mockito.when(restTemplate.exchange(
                ArgumentMatchers.eq("http://ip-api.com/json/125.22222225.0.1?fields=status,country,message"),
                ArgumentMatchers.eq(HttpMethod.GET),
                any(),
                ArgumentMatchers.<Class<IpResolverResponse>>any())).thenReturn(ResponseEntity.ok(failResponse));
    }

    @Test
    public void logCountryByIp(){
        resolver.logCountryByIp("125.0.0.1");

        Assert.assertTrue(memoryAppender.search("Country by IP resolved successfully. User country is: Japan").size() > 0);
    }

    @Test
    public void logCountryByIpThenFail(){
        resolver.logCountryByIp("125.22222225.0.1");

        Assert.assertTrue(memoryAppender.search("Failed to resolve country by IP. Error from service: invalid query").size() > 0);
    }

}
