package in.reqres.tests;

import in.reqres.models.CreateUsersBodyModel;
import in.reqres.models.CreateUsersResponseModel;
import in.reqres.models.UserResponseModel;
import in.reqres.models.UsersResponseModel;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.UsersSpec.*;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UsersTests {
    @Test
    void validCreateUser() {
        CreateUsersBodyModel requestBody = new CreateUsersBodyModel();
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        CreateUsersResponseModel response = step("Make /users POST request", () ->
                given(usersRequestSpec)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createUserResponseSpec)
                        .extract().as(CreateUsersResponseModel.class));

        step("Verify response", () -> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
        });
    }

    @Test
    void validListAllUsers() {
        UsersResponseModel response = step("Make GET /users request", () ->
                given(usersRequestSpec)
                        .when()
                        .get("/users")
                        .then()
                        .spec(usersResponseSpec)
                        .extract().as(UsersResponseModel.class));

        step("Verify response", () -> {
            assertEquals(1, response.getPage());
            assertEquals(6, response.getPerPage());
            assertEquals(12, response.getTotal());
            assertEquals(2, response.getTotalPages());
            assertEquals(1, response.getData().get(0).getId());
            assertEquals("george.bluth@reqres.in", response.getData().get(0).getEmail());
            assertEquals("George", response.getData().get(0).getFirstName());
            assertEquals("Bluth", response.getData().get(0).getLastName());
            assertEquals("https://reqres.in/img/faces/1-image.jpg", response.getData().get(0).getAvatar());
        });
    }

    @Test
    void validListPageUsers() {
        UsersResponseModel response = step("Make GET /users request for page=2", () ->
                given(usersRequestSpec)
                        .when()
                        .get("/users/?page=2")
                        .then()
                        .spec(usersResponseSpec)
                        .extract().as(UsersResponseModel.class));

        step("Verify response", () -> {
            assertEquals(2, response.getPage());
            assertEquals(6, response.getPerPage());
            assertEquals(12, response.getTotal());
            assertEquals(2, response.getTotalPages());
            assertEquals(7, response.getData().get(0).getId());
            assertEquals("michael.lawson@reqres.in", response.getData().get(0).getEmail());
            assertEquals("Michael", response.getData().get(0).getFirstName());
            assertEquals("Lawson", response.getData().get(0).getLastName());
            assertEquals("https://reqres.in/img/faces/7-image.jpg", response.getData().get(0).getAvatar());
        });
    }

    @Test
    void validShowUser() {
        UserResponseModel response = step("Make GET /users/2 request", () ->
                given(usersRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(usersResponseSpec)
                        .extract().as(UserResponseModel.class));

        step("Verify response", () -> {
            assertEquals(2, response.getData().getId());
            assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
            assertEquals("Janet", response.getData().getFirstName());
            assertEquals("Weaver", response.getData().getLastName());
            assertEquals("https://reqres.in/img/faces/2-image.jpg", response.getData().getAvatar());
        });
    }

    @Test
    void invalidShowUser() {
        step("Make invalid GET /users/100500 request", () ->
                given(usersRequestSpec)
                        .when()
                        .get("/users/100500")
                        .then()
                        .spec(invalidUserResponseSpec));
    }
}
