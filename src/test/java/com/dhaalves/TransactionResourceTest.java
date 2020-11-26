package com.dhaalves;

import static io.restassured.RestAssured.given;

import com.dhaalves.model.Currency;
import com.dhaalves.model.dto.TransactionDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class TransactionResourceTest {

  @Test
  public void getByUserId() {
    given().when().get("/transaction/user/dhaalves").then().statusCode(200);
  }

  @Test
  public void add() {
    TransactionDto transactionDto = TransactionDto.builder()
        .userId("dhaalves")
        .sourceCurrency(Currency.USD)
        .targetCurrency(Currency.BRL)
        .value(BigDecimal.valueOf(345.67))
        .build();
    given().contentType(ContentType.JSON).body(transactionDto).when().post("/transaction").then()
        .statusCode(200);
  }
}
