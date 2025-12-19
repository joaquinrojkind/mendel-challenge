package com.mendel;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionIntegrationTests {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	void testUpdateTransaction() {
		String body = "{\"amount\": 1200.05, \"type\": \"Credit\"}";

		given()
				.contentType(ContentType.JSON)
				.body(body)
				.when()
				.put("/transactions/10")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("transactionId", equalTo(10))
				.body("amount", equalTo(1200.05F))
				.body("type", equalTo("Credit"));
	}
}
