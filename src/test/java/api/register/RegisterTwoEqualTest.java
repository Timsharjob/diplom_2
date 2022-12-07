package api.register;

import api.auth.AuthClient;
import api.auth.AuthGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;

public class RegisterTwoEqualTest {

    private String token;
    private AuthClient authClient;

    @Before
    public void setUp() {
        authClient = new AuthClient();
    }

    @After
    public void cleanUp() {
        if (token != null) {
            authClient.deleteUser(token);
        }
    }

    @DisplayName("Регистрация двух одинаковых пользователей")
    @Test
    public void registerTwoEqualUserTest() {
        ValidatableResponse registerResponse = authClient.createUser(AuthGenerator.getRegisterDefault());
        token = registerResponse.extract().body().path("accessToken");
        ValidatableResponse registerResponse2 = authClient.createUser(AuthGenerator.getRegisterDefault());
        Assert.assertEquals(SC_FORBIDDEN, registerResponse2.extract().statusCode());
        Assert.assertEquals(false, registerResponse2.extract().body().path("success"));
    }
}
