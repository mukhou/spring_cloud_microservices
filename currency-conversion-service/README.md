

**Without using Eureka**
Earlier we were setting the url property of FeignClient to localhost:8000. However, we don't want to hardcode it and
want to configure it as well as load balance. So we use RibbonClient, pass it the name of the service we are
looking to invoke and then store ALL the urls(prefixes) in application.properties using:
<service-name-set-in-RibbonClient>.ribbon.listOfServers i.e currency-exchange-service.ribbon.listOfServers.
In this way when we call the service, RibbonCLient will balance multiple requests over instances
configured in the above property.

**Using Eureka**
Ribbon asks Eureka(naming server) for the instances of the currency exchange service and gets that info.
It then uses it to invoke the currency exchange service to get all the data required to do the conversion.
**Note**: with Eureka, the ribbon.listOfServers property is removed from application.properties, instead another property
is added which sets the url of the naming service where this app can register itself as well as discover those services
which it needs to connect with: eureka.client.service-url.default-zone
  
  
**Helpful URLs to hit**  

//currency exchange service
http://localhost:8000/currency-exchange/from/EUR/to/INR

//with api gateway proxy, to talk to currency exch service
http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/INR

//with api gateway proxy, call being routed through the gateway
http://localhost:8100/currency-converter-feign/from/EUR/to/INR/qty/10

//with  api gateway
http://localhost:8765/currency-exchange-service/currency-exchange/from/EUR/to/INR

//execute API gateway before request hits
http://localhost:8765/currency-conversion-service/currency-converter-feign/from/EUR/to/INR/qty/10
 