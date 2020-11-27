package com.dhaalves.service;

import com.dhaalves.model.CurrencySymbol;
import com.dhaalves.model.dto.ExchangeRatesDto;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/")
@RegisterRestClient(configKey = "exchangerates-api")
public interface ExchangeRatesService {

  @GET
  @Path("latest")
  @Produces(MediaType.APPLICATION_JSON)
  @CircuitBreaker(requestVolumeThreshold = 4)
  ExchangeRatesDto getExchangeRates(@QueryParam("base") CurrencySymbol sourceCurrency);
}