package com.dhaalves.resources;

import com.dhaalves.model.dto.TransactionDto;
import com.dhaalves.model.entity.Transaction;
import com.dhaalves.model.mapper.TransactionMapper;
import com.dhaalves.service.ExchangeRatesService;
import java.math.BigDecimal;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("transaction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResouce {

  private static final Logger LOGGER = Logger.getLogger(TransactionResouce.class);

  @Inject
  @RestClient
  private ExchangeRatesService ratesService;

  /**
   * Endpoint responsible to return all the transactions from a given user
   *
   * @param userId
   * @return
   */
  @GET
  @Path("/user/{userId}")
  public List<Transaction> getByUserId(@NotEmpty @PathParam("userId") String userId) {
    // using Active Record Pattern
    return Transaction.findByUserId(userId);
  }

  /**
   * Endpoint responsible to receive the request from the client for a currency conversion, it calls an
   * external API for exchange rates and persists the transaction.
   *
   * @param transactionDto
   * @return
   */
  @POST
  @Transactional
  public Response add(@Valid TransactionDto transactionDto) {
    try {
      //map transactionDto attributes to transaction
      Transaction transaction =
          TransactionMapper.INSTANCE.transactionDtoToTransaction(transactionDto);

      BigDecimal rate = ratesService.getExchangeRates(transaction.getSourceCurrency())
          .getRate(transaction.getTargetCurrency());
      LOGGER.info(String.format(
          "ExchangeRatesService#getExchangeRates(%s) returned successfully, the rate for %s is %s",
          transaction.getSourceCurrency(), transaction.getTargetCurrency(), rate));

      transaction.setExchangeRate(rate);

      Transaction.persist(transaction);
      LOGGER.info(String.format("Transaction %s persisted successfully", transaction.getId()));

      return Response.status(Status.CREATED).entity(transaction).build();

      // if any other thing goes wrong, return HTTP 400 with the error message on response body
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      return Response.status(Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
    }
  }
}
