package api.orders;

import api.auth.AuthClient;
import api.auth.AuthGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class GetOrdersTest {
    private AuthClient authClient;
    private OrdersClient ordersClient;
    private String token;

    @Before
    public void setUp() {
        authClient = new AuthClient();
        ordersClient = new OrdersClient();
    }

    @After
    public void cleanUp() {
        if (token != null) {
            authClient.delete(token);
        }
    }

    @DisplayName("Получение заказов пользователя с авторизацией")
    @Test
    public void getOrdersWithTokenTest() {
        ValidatableResponse registerResponse = authClient.create(AuthGenerator.getRegisterDefault());
        token = registerResponse.extract().body().path("accessToken");
        ValidatableResponse getOrdersResponse = ordersClient.getOrdersWithToken(token);
        Assert.assertEquals(SC_OK, getOrdersResponse.extract().statusCode());
        Assert.assertEquals(true, getOrdersResponse.extract().body().path("success"));
    }

    @DisplayName("Получение заказов пользователя без авторизации")
    @Test
    public void getOrdersWithoutTokenTest() {
        ValidatableResponse getOrdersResponse = ordersClient.getOrdersWithoutToken();
        Assert.assertEquals(SC_UNAUTHORIZED, getOrdersResponse.extract().statusCode());
        Assert.assertEquals(false, getOrdersResponse.extract().body().path("success"));
    }
}
