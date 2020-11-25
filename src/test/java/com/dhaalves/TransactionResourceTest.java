package com.dhaalves;

import static io.restassured.RestAssured.given;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class TransactionResourceTest {

  @Test
  public void getByUserId() {
    given().when().get("/transaction/user/dhaalves").then().statusCode(200);
  }

  @Test
  public void add() {
    given().when().post("/add").then().statusCode(200);
  }
}
