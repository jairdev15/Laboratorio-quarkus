package com.retailx.client;

import com.retailx.client.dto.ClientDTO;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ClientResourceTest {

    @Test
    public void testCreateAndList() {
        // JSON para crear un cliente
        String body = """
        {
            "name": "Test User",
            "email": "test@example.com",
            "document": "DNI-123"
        }
        """;

        // Crear cliente
        RestAssured.given()
                .header("Content-Type", "application/json")
                .header("X-API-KEY", "TEST_API_KEY")
                .body(body)
                .when()
                .post("/api/clients")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("name", equalTo("Test User"))
                .body("email", equalTo("test@example.com"))
                .body("document", equalTo("DNI-123"));

        // Listar clientes y comprobar que hay al menos uno
        RestAssured.given()
                .header("X-API-KEY", "TEST_API_KEY")
                .when()
                .get("/api/clients")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));
    }
}
