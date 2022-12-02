package api.auth;

public class AuthGenerator {

    public static Auth getRegisterDefault() {
        return new Auth("TestTim322322@test.ru", "2222", "TestTim");
    }

    public static Auth getRegisterWithoutEmail() {
        Auth auth = getRegisterDefault();
        auth.setEmail(null);
        return auth;
    }

    public static Auth getRegisterWithoutPassword() {
        Auth auth = getRegisterDefault();
        auth.setPassword(null);
        return auth;
    }

    public static Auth getRegisterWithoutName() {
        Auth auth = getRegisterDefault();
        auth.setName(null);
        return auth;
    }

    public static Auth getLoginDefault() {
        return new Auth("TestTim322322@test.ru", "2222");
    }

    public static Auth getLoginWithoutEmail() {
        Auth auth = getLoginDefault();
        auth.setEmail(null);
        return auth;
    }

    public static Auth getLoginWithoutPassword() {
        Auth auth = getLoginDefault();
        auth.setPassword(null);
        return auth;
    }

    public static Auth getLoginWrongData() {
        Auth auth = getLoginDefault();
        auth.setEmail("123");
        return auth;
    }

    public static Auth getRefreshUserAllData() {
        return new Auth("Test322322322@test.ru", "4444", "TestTim2");
    }

    public static Auth getRefreshUserEmail() {
        Auth auth = getRefreshUserAllData();
        auth.setPassword(null);
        auth.setName(null);
        return auth;
    }

    public static Auth getRefreshUserPassword() {
        Auth auth = getRefreshUserAllData();
        auth.setEmail(null);
        auth.setName(null);
        return auth;
    }

    public static Auth getRefreshUserName() {
        Auth auth = getRefreshUserAllData();
        auth.setPassword(null);
        auth.setEmail(null);
        return auth;
    }
}

