package tbc.ge.itacademy.SelenideTests2;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import ge.tbc.itacademy.data.Constants;
import ge.tbc.itacademy.listeners.CustomTestListener;
import ge.tbc.itacademy.util.ModdedAllureSelenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;
import java.util.ArrayList;
import java.util.List;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.Assert.assertEquals;
@Listeners({CustomTestListener.class})
@Test(groups = {"Parent Group"})
public class SelenideTests2 {
    SoftAssert sfa = new SoftAssert();

    @Epic("Order Mechanics")
    @Feature("Validation")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Order Mechanics Validation", priority = 1, groups = {"Selenide 2"})
    public void validateOrderMechanics(){
        open(Constants.site1);
        $(byText(Constants.pricing)).click();

        if ($("#onetrust-accept-btn-handler").isDisplayed()) {
            $("#onetrust-accept-btn-handler").click();
        }
        $("tr.Pricings-button > th.Complete").$x("./div/a").hover().click();

        String real = $(".e2e-total-price").text();
        sfa.assertEquals(real, Constants.wanted, "Prices are not equal");
        sfa.assertAll();
    }

    @Epic("Chained Locators")
    @Feature("Validation")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Chained Locators Validation", priority = 2, groups = {"Selenide 2"})
    public void chainedLocatorsTest() {
        open(Constants.site2);

        $$("div.rt-tr-group")
                .filterBy(text(Constants.bookPublisher))
                .filterBy(text(Constants.wantedTitle))
                .forEach(book -> {
                    String title = book.$(".action-buttons").getText();
                    System.out.println(title);
                });

        ElementsCollection images = $$("div.rt-td img");

        for (int i = 0; i < images.size(); i++) {
            images.get(i).scrollTo();
            images.get(i).shouldBe(Condition.exist);
        }
    }

    @Epic("Soft Assertions")
    @Feature("Validation")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Soft Assertions Validation", priority = 3, groups = {"Selenide 2"})
    public void softAssertTest() {
        open(Constants.site2);
        ElementsCollection books = $$("div.rt-tr-group")
                .filterBy(text(Constants.bookPublisher));

        List<String> bookTitles = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            books.get(i).scrollTo();
            String bookTitle = books.get(i).find("span.mr-2").text();
            if (bookTitle.contains(Constants.wantedTitle)) {
                bookTitles.add(bookTitle);
            }
        }
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(bookTitles.size(), 10, "Size is not equal to 10");

        String firstBook = String.valueOf(bookTitles.get(0));
        softAssert.assertEquals(firstBook, "Learning JavaScript Design Patterns");
        softAssert.assertAll();
    }

    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new ModdedAllureSelenide());

    }

}