package api.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;

@RunWith(Parameterized.class)
public class OrdersWithoutTokenTest {

    private final Orders orders;
    private OrdersClient ordersClient;
    private final int statusCode;
    private final boolean isSuccess;

    public OrdersWithoutTokenTest(Orders orders, int statusCode, boolean isSuccess) {
        this.orders = orders;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1} ,{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {OrdersGenerator.getDefaultOrders(), SC_OK, true},
                {OrdersGenerator.getEmptyOrders(), SC_BAD_REQUEST, false}
        };
    }

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @DisplayName("Создание заказа без авторизации")
    @Test
    public void createOrdersWithoutTokenTest() {
        ValidatableResponse createOrdersResponse = ordersClient.createWithoutToken(orders);
        Assert.assertEquals(statusCode, createOrdersResponse.extract().statusCode());
        Assert.assertEquals(isSuccess, createOrdersResponse.extract().body().path("success"));
    }
}
