package com.mendel;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TransactionIntegrationTests {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void testUpdateTransaction_success_noParentId() {
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
				.body("type", equalTo("Credit"))
				.body("parentId", equalTo(null));
	}

	@Test
	public void testUpdateTransaction_success_withParentId() {
		String body = "{\"amount\": 1200.05, \"type\": \"Credit\", \"parentId\": 11 }";

		given()
				.contentType(ContentType.JSON)
				.body(body)
				.when()
				.put("/transactions/10")
				.then()
				.statusCode(HttpStatus.OK.value())
				.body("transactionId", equalTo(10))
				.body("amount", equalTo(1200.05F))
				.body("type", equalTo("Credit"))
				.body("parentId", equalTo(11));
	}

	@Test
	public void testFindTransactionsByType_success_noResults() {

		given()
				.contentType(ContentType.JSON)
				.when()
				.get("/transactions/types/Credit")
				.then()
				.assertThat()
				.body("$", Matchers.hasSize(0));
	}

	@Test
	public void testFindTransactionsByType_success_withResults() {

		// Setup test data by creating two transactions

		String body1 = "{\"amount\": 1200.05, \"type\": \"Credit\" }";

		given()
			.contentType(ContentType.JSON)
			.body(body1)
			.when()
			.put("/transactions/10")
			.then()
			.statusCode(200);

		String body2 = "{\"amount\": 1200.05, \"type\": \"Debit\", \"parentId\": 10 }";

		given()
				.contentType(ContentType.JSON)
				.body(body2)
				.when()
				.put("/transactions/11")
				.then()
				.statusCode(200);

		// Send request for types "Credit" and "Debit"

		given()
				.contentType(ContentType.JSON)
				.when()
				.get("/transactions/types/Credit")
				.then()
				.assertThat()
				.body("$", Matchers.hasSize(1))
				.body("$", Matchers.hasItem(10));

		given()
				.contentType(ContentType.JSON)
				.when()
				.get("/transactions/types/Debit")
				.then()
				.assertThat()
				.body("$", Matchers.hasSize(1))
				.body("$", Matchers.hasItem(11));
	}

	@Test
	public void testSumTransactions_success() {

		// Setup test data by creating three transactions

		String body1 = "{\"amount\": 1200.05, \"type\": \"Credit\" }";

		given()
				.contentType(ContentType.JSON)
				.body(body1)
				.when()
				.put("/transactions/10")
				.then()
				.statusCode(200);

		String body2 = "{\"amount\": 1200.05, \"type\": \"Debit\", \"parentId\": 10 }";

		given()
				.contentType(ContentType.JSON)
				.body(body2)
				.when()
				.put("/transactions/11")
				.then()
				.statusCode(200);

		String body3 = "{\"amount\": 1200.05, \"type\": \"Debit\", \"parentId\": 10 }";

		given()
				.contentType(ContentType.JSON)
				.body(body2)
				.when()
				.put("/transactions/12")
				.then()
				.statusCode(200);

		// Send request for sum of the first transaction with related parent ids

		given()
				.contentType(ContentType.JSON)
				.when()
				.get("/transactions/sum/10")
				.then()
				.assertThat()
				.body("sum", Matchers.equalTo(3600.15F));

		// Send request for sum of the second transaction (no related parent ids)

		given()
				.contentType(ContentType.JSON)
				.when()
				.get("/transactions/sum/11")
				.then()
				.assertThat()
				.body("sum", Matchers.equalTo(1200.05F));
	}

	@Test
	public void testSumTransactions_transactionNotFound() {

		given()
				.contentType(ContentType.JSON)
				.when()
				.get("/transactions/sum/10")
				.then()
				.assertThat()
				.statusCode(404);
	}
}
