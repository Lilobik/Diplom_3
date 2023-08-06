import api.user.User;
import api.user.UserApi;
import api.user.UserData;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pom.LoginPage;
import pom.MainPage;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.Assert.assertTrue;

public class PersonalAccountTest {
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
        @Description("Проверь переход по клику на «Личный кабинет». Авторизованный Юзер")
        public void checkPersonalAccount() {
            boolean isPersonalAccount = open(LoginPage.LOGIN_URL, LoginPage.class)
                    .setEmailUser(user.getEmail())
                    .setPasswordUser(user.getPassword())
                    .clickBtnLogin()
                    .clickBtnPersonalArea()
                    .isUrlProfilePage();

            assertTrue(isPersonalAccount);
        }

        @Test
        @Description("Проверь переход по клику на «Личный кабинет». Не авторизованный Юзер")
        public void checkPersonalAccountUnauthorized() {
            boolean isPersonalAccount = open(MainPage.MAIN_URL, MainPage.class)
                    .clickBtnPersonalAreaNewUser()
                    .isUrlLoginPage();

            assertTrue(isPersonalAccount);
        }

        @Test
        @Description("Переход в конструктор из ЛК по клику на Конструктор")
        public void checkConstructorFromLK() {
            boolean isUrlMainPage = open(LoginPage.LOGIN_URL, LoginPage.class)
                    .setEmailUser(user.getEmail())
                    .setPasswordUser(user.getPassword())
                    .clickBtnLogin()
                    .clickBtnPersonalArea()
                    .clickConstructor()
                    .isUrlMainPage();

            assertTrue(isUrlMainPage);
        }

        @Test
        @Description("Клик по логотипу Stellar Burgers ведет в конструктор")
        public void checkConstructorFromLogo() {
            boolean isUrlMainPage = open(LoginPage.LOGIN_URL, LoginPage.class)
                    .setEmailUser(user.getEmail())
                    .setPasswordUser(user.getPassword())
                    .clickBtnLogin()
                    .clickBtnPersonalArea()
                    .clicklogoStellarBurgers()
                    .isUrlMainPage();

            assertTrue(isUrlMainPage);
        }

    }
