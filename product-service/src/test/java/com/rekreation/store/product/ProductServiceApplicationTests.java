package com.rekreation.store.product;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

  @ServiceConnection static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");

  static {
    mongoDBContainer.start();
  }

  @LocalServerPort private Integer port;

  @BeforeEach
  void setup() {
    RestAssured.baseURI = "http://localhost";
    RestAssured.port = port;
  }

  @Test
  void shouldCreateProduct() {
    // given
    String requestBody =
        """
				{
					"name": "IPhone15",
					"description": "Best I phone",
					"price": 50000
    		}
				""";

    // when
    RestAssured.given()
        .contentType("application/json")
        .body(requestBody)
        .when()
        .post("/api/product")
        .then()
        .statusCode(201)
        .body("id", Matchers.notNullValue())
        .body("name", Matchers.equalTo("IPhone15"))
        .body("description", Matchers.equalTo("Best I phone"))
        .body("price", Matchers.equalTo(50000));
  }
}
