import api.user.User;
import api.user.UserApi;
import api.user.UserData;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pom.MainPage;
import pom.RecoveryPassPage;
import pom.RegistrationPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class LoginTest {

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
        getWebDriver().quit();
        UserData userData = new UserData(user.getEmail(), user.getPassword());
        Response response = userApi.loginUser(userData);
        if (response.body().jsonPath().getString("accessToken") != null) {
            userApi.deleteUser(response);
        }
    }

    @Test
    @Description("Логин на главной странице")
    public void loginOnPageTest() {
        boolean isUrlMainPage = open(MainPage.MAIN_URL,MainPage.class)
                .clickBtnSignIn()
                .setEmailUser(user.getEmail())
                .setPasswordUser(user.getPassword())
                .clickBtnLogin()
                .isUrlMainPage();

        assertTrue(isUrlMainPage);
    }


    @Test
    @Description("Логин в личном кабинете")
    public void loginInPersonalCabinetTest() {
        boolean isUrlMainPage = open(MainPage.MAIN_URL,MainPage.class)
                .clickBtnPersonalAreaNewUser()
                .setEmailUser(user.getEmail())
                .setPasswordUser(user.getPassword())
                .clickBtnLogin()
                .isUrlMainPage();

        assertTrue(isUrlMainPage);
    }

    @Test
    @Description("Логин на странице регистрации")
    public void loginOnRegistrationPageTest() {
        boolean isUrlMainPage = open(RegistrationPage.REGISTER_URL, RegistrationPage.class)
                .clickRegistrationFormLinkToLoginForm()
                .setEmailUser(user.getEmail())
                .setPasswordUser(user.getPassword())
                .clickBtnLogin()
                .isUrlMainPage();

        assertTrue(isUrlMainPage);
    }


    @Test
    @Description("Логин на странице восстановления")
    public void loginOnRecoveryPageTest() {
        boolean isUrlMainPage = open(RecoveryPassPage.RECOVERY_PASS_URL, RecoveryPassPage.class)
                .clickPasswordRecoveryFormLinkToLoginForm()
                .setEmailUser(user.getEmail())
                .setPasswordUser(user.getPassword())
                .clickBtnLogin()
                .isUrlMainPage();

        assertTrue(isUrlMainPage);
    }
}
