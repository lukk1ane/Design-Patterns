package tbc.ge.itacademy.SelenideTests2;

import com.codeborne.selenide.*;
import ge.tbc.itacademy.data.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
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


@Test (groups = {"Parent Group"})
public class SelenideTests2 {
    SoftAssert sfa = new SoftAssert();

    @Test(description = "orderMechanicsValidation", priority = 1, groups = {"Selenide 2"})
    public void  validateOrderMechanics(){
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

    @Test(description = "chainedLocatorValidation", priority = 2, groups = {"Selenide 2"})

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



    @Test(description = "softAssertValidation", priority = 3, groups = {"Selenide 2"})
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
}