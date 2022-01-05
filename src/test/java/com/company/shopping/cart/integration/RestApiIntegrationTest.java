package com.company.shopping.cart.integration;

import com.company.shopping.cart.model.Course;
import com.company.shopping.cart.model.CreateOrderRequest;
import com.company.shopping.cart.model.CreateOrderResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestApiIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = this.port;
    }

    @Test
    void shouldCreateAndFindOrder() {
        CreateOrderRequest request = CreateOrderRequest.builder()
                .products(List.of(Course.Math, Course.Physics))
                .build();

        CreateOrderResponse response = RestAssured
                .given()
                .log().all()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/api/orders")
                .then()
                .log().all()
                .statusCode(201)
                .extract().as(CreateOrderResponse.class);

        long orderId = response.getOrderId();

        RestAssured
                .given()
                .log().all()
                .get("/api/orders/" + orderId)
                .then()
                .log().all()
                .statusCode(200)
                .assertThat()
                .body("totalCost", equalTo(85))
                .body("products", equalTo(List.of(Course.Math.name(), Course.Physics.name())));
    }

    @Test
    void shouldNotFindOrder() {
        long orderId = 99999L;

        RestAssured
                .given()
                .log().all()
                .get("/api/orders/" + orderId)
                .then()
                .log().all()
                .statusCode(404);
    }
}
