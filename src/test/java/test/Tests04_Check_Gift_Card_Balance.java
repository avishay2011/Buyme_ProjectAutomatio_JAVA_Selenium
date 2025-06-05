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
public class Tests04_Check_Gift_Card_Balance extends BaseTest {

    @BeforeMethod
    public void checkPageReadyAndPreConditions() throws ParserConfigurationException, IOException, SAXException {
        Assert.assertTrue(homePage.pageLoadedWithNoErrors());
        Assert.assertTrue(homePage.homePageIsDisplayed());
        checkGiftCardBalanceFlow.verifyGiftCardBalanceScreenFlow();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Check not exists gift card Code and verify that the correct error message appears")
    public void test01_ErrorShown_ForNonExistentGiftCardCode() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        checkGiftCardBalanceFlow.fillGiftCardDetails_WrongGiftCardCode();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Check exists gift card Code that allready used and verify that the correct message appears")
    /////Was supposed to check an existing expired voucher, but since I didn’t have one – I checked a voucher that was already fully used instead
    public void test02_ErrorShown_ForExistentGiftCardCode_ThatAllreadyUsed() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        checkGiftCardBalanceFlow.fillGiftCardDetails_UsedGiftCardCode();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check valid gift card code and verify that the right message appears with the current balanced")
    public void test03_ValidGiftCard() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
       checkGiftCardBalanceFlow.fillGiftCardDetails_ValidGiftCardCode();
    }
}