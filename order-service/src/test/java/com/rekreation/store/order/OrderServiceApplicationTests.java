package com.rekreation.store.order;

import com.rekreation.store.order.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

  @ServiceConnection static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

  static {
    mySQLContainer.start();
  }

  @LocalServerPort private Integer port;

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void shouldPlaceOrder() {
    // given
    String requestBody =
        """
				{
					"price": 50000,
					"skuCode": "iphone_15",
					"quantity": 1
				}
				""";

    InventoryClientStub.stubInventoryCall("iphone_15", 1);
    // when
    var responseBodyString =
        RestAssured.given()
            .contentType("application/json")
            .body(requestBody)
            .when()
            .post("/api/order")
            .then()
            .statusCode(201)
            .extract()
            .body()
            .asString();

    MatcherAssert.assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));
  }
}
