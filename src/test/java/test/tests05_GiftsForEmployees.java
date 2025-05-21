package test;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import verifications.Verifications;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import static utils.Utilities.readFromThisFile;

@Listeners(utils.Listeners.class)
public class tests05_GiftsForEmployees extends BaseTest{

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
          homePage.navigateToGiftsForEmployees();
          giftsForEmployees_page.click_JoinNowButton();
          Verifications.verifyTrue(giftsForEmployees_page.isEmployeesAmountFieldISDisplayed(),"Verify Elmployees amount field visibilty");
          Verifications.verifyTrue(driver.getCurrentUrl().contains(readFromThisFile("giftsForEmployeesPageUrl")),"Verify Gifts for employees page opens");
          Verifications.assertAll();
    }
}
