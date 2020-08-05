/* 
User: Urmi
Date: 7/4/2020 
Time: 3:37 PM
*/

package com.in28minutes.microservices.limitsservice.controller;

import com.in28minutes.microservices.limitsservice.config.Configuration;
import com.in28minutes.microservices.limitsservice.model.LimitsConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsConfigurationController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitsConfiguration retrieveLimitsFromConfigurations(){
        return LimitsConfiguration.builder().maximum(configuration.getMaximum()).minimum(configuration.getMinimum()).build();
    }

    @GetMapping("/fault-tolerance-example")
    @HystrixCommand(fallbackMethod = "fallbackRetrieveConfiguration")
    public LimitsConfiguration retrieveConfiguration(){
        throw new RuntimeException("Not available");
    }

    public LimitsConfiguration fallbackRetrieveConfiguration(){
        return LimitsConfiguration.builder().minimum(9).maximum(999).build();
    }
}
