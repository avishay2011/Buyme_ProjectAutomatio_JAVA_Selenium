package test;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


@Listeners(utils.Listeners.class)
public class Tests03_SelectGift extends BaseTest {
    @BeforeMethod
    public void checkPageReadyAndPreConditions() throws ParserConfigurationException, IOException, SAXException {
        Assert.assertTrue(homePage.pageLoadedWithNoErrors());
        Assert.assertTrue(homePage.homePageIsDisplayed());
        selectGiftFlow.giftListAmountAndCardDisplayFlow();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Select gift without enter amount and verify that expected error message appears")
    public void test01_Verify_That_Amount_Field_IsMandatory() throws ParserConfigurationException, IOException, SAXException {
        selectGiftFlow.selectNoAmountEntered_Flow();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Select gift for someone else and verify next step is enabled after required fields are filled.")
    public void test02_Buy_Gift_For_Friend() throws ParserConfigurationException, IOException, SAXException {
        selectGiftFlow.verifyMandatoryInputsAppears_Flow();
        selectGiftFlow.fillGiftCardReceiverFormFlow();
    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Select the option gift for myself  and verify that the correct message appears")
    public void test03_Buy_Gift_ForMyself() throws ParserConfigurationException, IOException, SAXException {
        selectGiftFlow.selectGiftForMyselfFlow();
    }
}
