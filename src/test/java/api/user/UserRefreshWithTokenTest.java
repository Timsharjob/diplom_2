package api.user;

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

@RunWith(Parameterized.class)
public class UserRefreshWithTokenTest {
    private String token;
    private final Auth auth;
    private final int statusCode;
    private final boolean isSuccess;
    private AuthClient authClient;

    public UserRefreshWithTokenTest(Auth auth, int statusCode, boolean isSuccess) {
        this.auth = auth;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1} ,{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {AuthGenerator.getRefreshUserAllData(), SC_OK, true},
                {AuthGenerator.getRefreshUserEmail(), SC_OK, true},
                {AuthGenerator.getRefreshUserPassword(), SC_OK, true},
                {AuthGenerator.getRefreshUserName(), SC_OK, true}
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

    @DisplayName("Изменение пользователя с авторизацией")
    @Test
    public void refreshUserWithTokenTest() {
        ValidatableResponse registerResponse = authClient.create(AuthGenerator.getRegisterDefault());
        token = registerResponse.extract().body().path("accessToken");
        ValidatableResponse refreshUserResponse = authClient.refreshUserWithToken(auth, token);
        Assert.assertEquals(statusCode, refreshUserResponse.extract().statusCode());
        Assert.assertEquals(isSuccess, refreshUserResponse.extract().body().path("success"));
    }
}
