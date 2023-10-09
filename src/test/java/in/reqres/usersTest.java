package in.reqres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.is;


import static io.restassured.RestAssured.given;

public class usersTest {
    @BeforeAll
    static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void validCreateUser() {
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
            .log().uri()
            .log().method()
            .log().body()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/users")
        .then()
            .log().status()
            .log().body()
            .statusCode(201)
            .body("name", is("morpheus"))
            .body("job", is("leader"));
    }
    @Test
    void validListAllUsers() {
        given()
            .log().uri()
            .log().method()
        .when()
            .get("/users")
        .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .body("page", is("1"))
            .body("total", is("12"))
            .body("data.id", is("1"));
    }
    @Test
    void validListPageUsers() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("/users/?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is("2"))
                .body("total", is("12"))
                .body("data.id", is("7"));
    }

    @Test
    void validShowUser() {
        given()
                .log().uri()
                .log().method()
            .when()
                .get("/users/3")
            .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(3));
    }
    @Test
    void invalidShowUser() {
        given()
                .log().uri()
                .log().method()
            .when()
                .get("/users/100500")
            .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
