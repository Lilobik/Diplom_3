import api.user.User;
import api.user.UserApi;
import api.user.UserData;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pom.RegistrationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class RegistrationUserTest {
    private User user;
    private UserApi userApi;

    @Before

    public void setUp() {
        user = User.getRandomUser();
        userApi = new UserApi();
    }

    @After
    public void tearDown() {
        getWebDriver().quit();
        UserData userData = new UserData(user.getEmail(), user.getPassword());
        Response response = userApi.loginUser(userData);
        if (response.body().jsonPath().getString("accessToken") != null) {
            userApi.deleteUser(response);
        }
    }

    @Test
    @Description("После успешной регистрации открывается страница авторизации")
    public void succesRegistrationTest() {

        boolean isUrlLoginPage = open(RegistrationPage.REGISTER_URL, RegistrationPage.class)
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(user.getPassword())
                .clickBtnRegister()
                .isUrlLoginPage();

        assertTrue(isUrlLoginPage);
    }


    @Test
    @Description("Регистрация не происходит при введении некорректного пароля, возвращается ошибка")
    public void errorInvalidPasswordRegistrationTest() {
        String password = RandomStringUtils.randomAlphabetic(5);
        boolean isMessagError = open(RegistrationPage.REGISTER_URL, RegistrationPage.class)
                .setName(user.getName())
                .setEmail(user.getEmail())
                .setPassword(password)
                .isMessagError();

        assertTrue("Некорректный пароль", isMessagError);
    }

}
