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

import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@RunWith(Parameterized.class)
public class UserRefreshWithoutTokenTest {

    private String token;
    private final Auth auth;
    private final int statusCode;
    private final boolean isSuccess;
    private AuthClient authClient;

    public UserRefreshWithoutTokenTest(Auth auth, int statusCode, boolean isSuccess) {
        this.auth = auth;
        this.statusCode = statusCode;
        this.isSuccess = isSuccess;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0}, {1} ,{2}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {AuthGenerator.getRefreshUserAllData(), SC_UNAUTHORIZED, false},
                {AuthGenerator.getRefreshUserEmail(), SC_UNAUTHORIZED, false},
                {AuthGenerator.getRefreshUserPassword(), SC_UNAUTHORIZED, false},
                {AuthGenerator.getRefreshUserName(), SC_UNAUTHORIZED, false}
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

    @DisplayName("Изменение пользователя без авторизации")
    @Test
    public void refreshUserWithoutTokenTest() {
        ValidatableResponse registerResponse = authClient.create(AuthGenerator.getRegisterDefault());
        token = registerResponse.extract().body().path("accessToken");
        ValidatableResponse refreshUserResponse = authClient.refreshUserWithoutToken(auth);
        Assert.assertEquals(statusCode, refreshUserResponse.extract().statusCode());
        Assert.assertEquals(isSuccess, refreshUserResponse.extract().body().path("success"));
    }
}
