package api.orders;

import api.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient extends Client {

    private static final String PATH_ORDERS = "api/orders";

    @Step
    public ValidatableResponse createWithToken(Orders orders, String token) {
        return given()
                .spec(getSpec())
                .header("Authorization", token)
                .body(orders)
                .when()
                .post(PATH_ORDERS)
                .then();
    }

    @Step
    public ValidatableResponse createWithoutToken(Orders orders) {
        return given()
                .spec(getSpec())
                .body(orders)
                .when()
                .post(PATH_ORDERS)
                .then();
    }

    @Step
    public ValidatableResponse getOrdersWithToken(String token) {
        return given()
                .spec(getSpec())
                .header("Authorization", token)
                .when()
                .get(PATH_ORDERS)
                .then();
    }

    @Step
    public ValidatableResponse getOrdersWithoutToken() {
        return given()
                .spec(getSpec())
                .when()
                .get(PATH_ORDERS)
                .then();
    }
}
