/* 
User: Urmi
Date: 7/5/2020 
Time: 10:56 AM
*/

package com.in28minutes.microservices.currencyconversionservice.controller;

import com.in28minutes.microservices.currencyconversionservice.feign.CurrencyExchangeServiceProxy;
import com.in28minutes.microservices.currencyconversionservice.model.CurrencyConversion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class CurrencyConversionController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeServiceProxy proxy;

    @GetMapping("/currency-converter/from/{from}/to/{to}/qty/{qty}")
    public CurrencyConversion convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal qty){
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
                CurrencyConversion.class, uriVariables);
        CurrencyConversion response = responseEntity.getBody();
        return CurrencyConversion.builder().id(response.getId()).from(from).to(to)
                .conversionMultiple(response.getConversionMultiple())
                .qty(qty)
                .totalAmont(qty.multiply(response.getConversionMultiple()))
                .port(response.getPort())
                .build();
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/qty/{qty}")
    public CurrencyConversion convertCurrencyFeign(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal qty){
        CurrencyConversion response = proxy.retrieveExchangeValue(from, to);
        logger.info("{}", response);
        return CurrencyConversion.builder().id(response.getId()).from(from).to(to)
                .conversionMultiple(response.getConversionMultiple())
                .qty(qty)
                .totalAmont(qty.multiply(response.getConversionMultiple()))
                .port(response.getPort())
                .build();
    }
}
