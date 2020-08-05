/* 
User: Urmi
Date: 7/5/2020 
Time: 12:18 PM
*/

package com.in28minutes.microservices.currencyconversionservice.feign;

/**
 * Proxy class used by Feign to talk to currency exchange microservice.
 * Notes:
 *  **Without using Eureka**
 *   Earlier we were setting the url property of FeignClient to localhost:8000. However, we don't want to hardcode it and
 *   want to configure it as well as load balance. So we use RibbonClient, pass it the name of the service we are
 *   looking to invoke and then store ALL the urls(prefixes) in application.properties using:
 *   <service-name-set-in-RibbonClient>.ribbon.listOfServers i.e currency-exchange-service.ribbon.listOfServers.
 *   In this way when we call the service, RibbonCLient will balance multiple requests over instances
 *   configured in the above property.
 *
 *   **Using Eureka**
 *   Ribbon asks Eureka(naming server) for the instances of the currency exchange service and gets that info.
 *   It then uses it to invoke the currency exchange service to get all the data required to do the conversion.
 *   Note: with Eureka, the ribbon.listOfServers property is removed from application.properties, instead another property
 *   is added which sets the url of the naming service where this app can register itself as well as discover those services
 *   which it needs to connect with: eureka.client.service-url.default-zone
 */

import com.in28minutes.microservices.currencyconversionservice.model.CurrencyConversion;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//this is without using RibbonClient, directly configuring the url of the currency-exchange-service
//@FeignClient(name = "currency-exchange-service", url = "localhost:8000")
//this is using RibbonClient, configuring the url of the currency-exchange-service in app.properties
//using currency-exchange-service.ribbon.listOfServers. This is used for load balancing.
//@FeignClient(name = "currency-exchange-service")
//with this we are asking FeignClient to talk to the API Gateway server, instead of talking directly to the
//currency exchange service. Thus our client changes from currency exchange service to the API gateway service
@FeignClient(name = "netflix-zuul-api-gateway-server")
@RibbonClient(name = "currency-exchange-service")
public interface CurrencyExchangeServiceProxy {

    //this was when we were directly talking to currency exchange service through Feign Client, without using
    //API Gateway
    //v1:
    //@GetMapping("/currency-exchange/from/{from}/to/{to}")
    //as we are using API Gateway to talk to currency exchange service, so now we need to append
    //the "name" of that application in the @GetMapping url, i. e "/currency-exchange-service"
    //thus the call is being routed through the gateway
    //v2:
    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

}
