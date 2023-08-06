import api.user.User;
import api.user.UserApi;
import api.user.UserData;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pom.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

public class LogoutTest {
    private User user;
    private UserApi userApi;

    @Before
    public void setUp() {
        user = User.getRandomUser();
        userApi = new UserApi();
        userApi.createUser(user);
    }

    @After
    public void tearDown() {
        UserData userData = new UserData(user.getEmail(), user.getPassword());
        Response response = userApi.loginUser(userData);
        if (response.body().jsonPath().getString("accessToken") != null) {
            userApi.deleteUser(response);
        }
    }

    @Test
    @Description("Выход из аккаунта -> переход на страницу входа")
    public void logout() {
        boolean isUrlLoginPage = open(LoginPage.LOGIN_URL, LoginPage.class)
                .setEmailUser(user.getEmail())
                .setPasswordUser(user.getPassword())
                .clickBtnLogin()
                .clickBtnPersonalArea()
                .clickLogout()
                .isUrlLoginPage();

        assertTrue(isUrlLoginPage);
    }

}
