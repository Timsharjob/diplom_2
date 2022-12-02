package api.register;

import api.auth.Auth;
import api.auth.AuthClient;
import api.auth.AuthGenerator;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

@RunWith(Parameterized.class)
public class RegisterTest {
    private String token;
    private final Auth auth;
    private final int statusCode;
    private final boolean isSuccess;
    private AuthClient authClient;

    public RegisterTest(Auth auth, int statusCode, boolean isSuccess) {
        this.auth = auth;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1} ,{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {AuthGenerator.getRegisterDefault(), SC_OK, true},
                {AuthGenerator.getRegisterWithoutEmail(), SC_FORBIDDEN, false},
                {AuthGenerator.getRegisterWithoutPassword(), SC_FORBIDDEN, false},
                {AuthGenerator.getRegisterWithoutName(), SC_FORBIDDEN, false}
        };
    }

    @Before
    public void setUp() {
        authClient = new AuthClient();
    }

    @After
    public void cleanUp() {
        if (token != null) {
            authClient.delete(token);
        }
    }

    @DisplayName("Регистрация пользователя")
    @Test
    public void registerUserTest() {
        ValidatableResponse registerResponse = authClient.create(auth);
        token = registerResponse.extract().body().path("accessToken");
        Assert.assertEquals(statusCode, registerResponse.extract().statusCode());
        Assert.assertEquals(isSuccess, registerResponse.extract().body().path("success"));
    }
}
