/* 
User: Urmi
Date: 7/4/2020 
Time: 10:20 PM
*/

package com.in28minutes.microservices.currencyexchangeservice.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ExchangeValue {

    @Id
    private Long id;
    @Column(name = "currency_from")
    private String from;
    @Column(name="currency_to")
    private String to;
    private BigDecimal conversionMultiple;
    private int port;
}
