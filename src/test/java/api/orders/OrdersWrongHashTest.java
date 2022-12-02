package api.orders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class OrdersWrongHashTest {

    private OrdersClient ordersClient;

    @Before
    public void setUp() {
        ordersClient = new OrdersClient();
    }

    @DisplayName("Создание заказа с неверным хешем")
    @Test
    public void createOrdersWrongHashTest() {
        ValidatableResponse createOrdersResponse = ordersClient.createWithoutToken(OrdersGenerator.getWrongHashOrders());
        Assert.assertEquals(SC_INTERNAL_SERVER_ERROR, createOrdersResponse.extract().statusCode());
    }
}
