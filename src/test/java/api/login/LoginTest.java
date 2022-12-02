package api.login;

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

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@RunWith(Parameterized.class)
public class LoginTest {
    private String token;
    private final Auth auth;
    private final int statusCode;
    private final boolean isSuccess;
    private AuthClient authClient;

    public LoginTest(Auth auth, int statusCode, boolean isSuccess) {
        this.auth = auth;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1} ,{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {AuthGenerator.getLoginDefault(), SC_OK, true},
                {AuthGenerator.getLoginWithoutEmail(), SC_UNAUTHORIZED, false},
                {AuthGenerator.getLoginWithoutPassword(), SC_UNAUTHORIZED, false},
                {AuthGenerator.getLoginWrongData(), SC_UNAUTHORIZED, false}
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

    @DisplayName("Авторизация пользователя")
    @Test
    public void loginTest() {
        ValidatableResponse registerResponse = authClient.create(AuthGenerator.getRegisterDefault());
        token = registerResponse.extract().body().path("accessToken");
        ValidatableResponse loginResponse = authClient.login(auth);
        Assert.assertEquals(statusCode, loginResponse.extract().statusCode());
        Assert.assertEquals(isSuccess, loginResponse.extract().body().path("success"));
    }
}
