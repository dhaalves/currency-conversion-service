package com.dhaalves.resources;

import com.dhaalves.dto.TransactionDto;
import com.dhaalves.mapper.TransactionMapper;
import com.dhaalves.model.Transaction;
import io.vertx.core.json.JsonObject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("transaction")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResouce {

  public static final String EXCHANGE_RATE_API_URL =
      "https://api.exchangeratesapi.io/latest?base={base}";

  private Client client;

  @PostConstruct
  public void init() {
    client = ClientBuilder.newClient();
  }

  /**
   * Endpoint responsable to return all the transactions from a given user
   *
   * @param userId
   * @return
   */
  @GET
  @Path("/user/{userId}")
  public List<Transaction> getByUserId(@NotEmpty @PathParam("userId") String userId) {
    // using active record Pattern
    return Transaction.findByUserId(userId);
  }

  /**
   * Endpoint responsable to receive the request from the user for a currency conversion, it call
   * external API for exchange rates and persists the transaction.
   *
   * @param transactionDto
   * @return
   */
  @POST
  @Transactional
  public Response add(@Valid TransactionDto transactionDto) {
    try {
      // call external api
      Response exchangeRatesApiResponse =
          client
              .target(EXCHANGE_RATE_API_URL)
              .resolveTemplate("base", transactionDto.getSourceCurrency().name())
              .request()
              .get();

      if (exchangeRatesApiResponse.getStatus() == Status.OK.getStatusCode()) {

        // read json from api response
        Map<String, Object> entries =
            exchangeRatesApiResponse.readEntity(JsonObject.class).getJsonObject("rates").getMap();

        Transaction transaction =
            TransactionMapper.INSTANCE.transactionDtoToTransaction(transactionDto);

        transaction.setExchangeRate(
            BigDecimal.valueOf((Double) entries.get(transaction.getTargetCurrency().name())));

        Transaction.persist(transaction);

        return Response.ok().entity(transaction).build();
      }

      // if external api call is not sucessful HTTP != 200, foward their response;
      return exchangeRatesApiResponse;

      // if any other thing goes wrong, return HTTP 400 with the error message on response body
    } catch (Exception e) {
      Logger.getLogger(TransactionResouce.class.getName()).log(Level.SEVERE, e.getMessage(), e);
      return Response.status(Status.BAD_REQUEST).entity(e.getLocalizedMessage()).build();
    }
  }
}
