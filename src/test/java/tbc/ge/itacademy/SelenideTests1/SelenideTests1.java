package tbc.ge.itacademy.SelenideTests1;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import ge.tbc.itacademy.data.Constants;
import ge.tbc.itacademy.listeners.CustomTestListener;
import ge.tbc.itacademy.util.ModdedAllureSelenide;
import io.qameta.allure.*;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.testng.Assert.assertFalse;



@Listeners({CustomTestListener.class})
@Test (groups = {"Parent Group"})
public class SelenideTests1 {

    @Feature("Bundle Offers")
    @Epic("Validation")
    @Story("Validate Bundle Offers")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "OffersValidation",priority = 1, groups = {"Selenide 1"})
    public void validateBundleOffers(){
        WebDriverManager.chromedriver().setup();
        open(Constants.site1);
        getWebDriver().manage().window().maximize();
        $(byText(Constants.pricing)).click();

        String div1Texts = $("div.u-m-m0.u-m-mt1.u-pl1.u-pr0").text();

        assertFalse(div1Texts.contains(Constants.mockingText));
        SelenideElement demandVideos=$(byText(Constants.videoAccess));
    }

    @Feature("Individual Offers")
    @Epic("Validation")
    @Story("Validate Individual Offers")
    @Severity(SeverityLevel.NORMAL)
    @Test(description = "Validate Individual Offers", priority = 2, groups = {"Selenide 1"})
    public void validateIndividualOffers(){
        WebDriverManager.chromedriver().setup();
        open(Constants.site1);

        getWebDriver().manage().window().maximize();

        $(byText(Constants.pricing)).click();
        $(withText(Constants.productsText)).shouldBe(clickable).click();

        SelenideElement div1 = $x(Constants.firstDivXpath);
        SelenideElement div2 = $x(Constants.secondDivXpath);

        div1.shouldBe(Condition.visible).hover();
        $(byTitle(Constants.uiKendo)).shouldBe(Condition.image).should(Condition.appear);
        div2.shouldBe(Condition.visible).hover();
        $(byTitle(Constants.reactKendo)).shouldBe(Condition.image).should(Condition.appear);

        SelenideElement selectUI = div1.$(".Box-content > div.Dropdown");
        SelenideElement selectReact = div1.$(".Box-content > div.Dropdown");

        selectUI.should(exactText(Constants.priorText));
        selectReact.should(exactText(Constants.priorText));

        div1.$$x(Constants.h2SpansSupXpath).should(exactTexts(Constants.dollarSign, Constants.uiPrice));
        div2.$$x(Constants.h2SpansSupXpath).should(exactTexts(Constants.dollarSign, Constants.reactPrice));
    }


    public void setUp() {
        SelenideLogger.addListener("AllureSelenide", new ModdedAllureSelenide());

    }
}