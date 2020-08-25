package com.demo.payment.service.ipresolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IPResolver {
    private static final String IP_API_URL = "http://ip-api.com/json/";
    Logger logger = LoggerFactory.getLogger(IPResolver.class);
    private final RestTemplate restTemplate;

    public IPResolver() {
        this.restTemplate = new RestTemplate();
    }

    public void logCountryByIp(String ipAddress) {
        IpResolverResponse requestResult = makeRequestToIpApiService(ipAddress);
        if(requestResult.getStatus().equals("success")) {
            logger.info("Country by IP resolved successfully. User country is: " + requestResult.getCountry());
        } else {
            logger.info("Failed to resolve country by IP. Error from service: " + requestResult.getMessage());
        }
    }

    private IpResolverResponse makeRequestToIpApiService(String ipAddress) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        HttpEntity<IpResolverResponse> response = restTemplate.exchange(IP_API_URL + ipAddress +"?fields=status,country,message",
                                                    HttpMethod.GET,
                                                    entity,
                                                    IpResolverResponse.class);

        return response.getBody();
    }
}
