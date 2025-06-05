package test;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Listeners(utils.Listeners.class)
public class Tests05_GiftsForEmployees extends BaseTest{

    @BeforeMethod
    @Severity(SeverityLevel.BLOCKER)
    public void checkPageReady() throws ParserConfigurationException, IOException, SAXException {
        Assert.assertTrue(homePage.pageLoadedWithNoErrors());
        Assert.assertTrue(homePage.homePageIsDisplayed());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check not exists gift card Code and verify that the correct error message appears")
    public void test_OpenPageGiftsForEmplooyes() throws ParserConfigurationException, IOException, SAXException {
        giftsForEmployeesFlow.openBuyMeForBusinessFlow();
    }
}
