import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import pom.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.junit.Assert.assertTrue;

public class ConstructorTest {

    @After
    public void tearDown() {
        getWebDriver().quit();
    }

    @Test
    @Description("Открытие раздела Булки")
    public void openConstructorBunsTest() {
        boolean isDisplaySectionBuns = open(MainPage.MAIN_URL, MainPage.class)
                .clickSectionSauce()
                .clickSectionBuns()
                .isDisplaySectionBuns();

        assertTrue(isDisplaySectionBuns);
    }


    @Test
    @Description("Открытие раздела Соусы")
    public void openConstructorSaucesTest() {
        boolean isDisplaySectionSauce = open(MainPage.MAIN_URL, MainPage.class)
                .clickSectionSauce()
                .isDisplaySectionSauce();

        assertTrue(isDisplaySectionSauce);
    }

    @Test
    @Description("Открытие раздела Начинки")
    public void openConstructorFillingsTest() {
        boolean isDisplaySectionFilling = open(MainPage.MAIN_URL, MainPage.class)
                .clickSectionFilling()
                .isDisplaySectionFilling();

        assertTrue(isDisplaySectionFilling);
    }


}
