/* 
User: Urmi
Date: 7/5/2020 
Time: 10:58 AM
*/

package com.in28minutes.microservices.currencyconversionservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CurrencyConversion {
    private Long id;
    private String from;
    private String to;
    private BigDecimal conversionMultiple;
    private BigDecimal qty;
    private BigDecimal totalAmont;
    private int port;

}
