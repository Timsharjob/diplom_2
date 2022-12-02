package api.auth;

import api.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class AuthClient extends Client {
    private static final String PATH_REGISTER = "api/auth/register";
    private static final String PATH_USER = "api/auth/user";
    private static final String PATH_LOGIN = "api/auth/login";

    @Step
    public ValidatableResponse create(Auth auth) {
        return given()
                .spec(getSpec())
                .body(auth)
                .when()
                .post(PATH_REGISTER)
                .then();
    }

    @Step
    public ValidatableResponse delete(String token) {
        return given()
                .spec(getSpec())
                .header("Authorization", token)
                .when()
                .delete(PATH_USER)
                .then();
    }

    @Step
    public ValidatableResponse login(Auth auth) {
        return given()
                .spec(getSpec())
                .body(auth)
                .when()
                .post(PATH_LOGIN)
                .then();
    }

    @Step
    public ValidatableResponse refreshUserWithToken(Auth auth, String token) {
        return given()
                .spec(getSpec())
                .header("Authorization", token)
                .body(auth)
                .when()
                .patch(PATH_USER)
                .then();
    }

    @Step
    public ValidatableResponse refreshUserWithoutToken(Auth auth) {
        return given()
                .spec(getSpec())
                .body(auth)
                .when()
                .patch(PATH_USER)
                .then();
    }
}
