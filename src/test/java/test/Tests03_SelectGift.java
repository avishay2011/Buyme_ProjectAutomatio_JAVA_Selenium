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
public class Tests03_SelectGift extends BaseTest {
    @BeforeMethod
    public void checkPageReadyAndPreConditions() throws ParserConfigurationException, IOException, SAXException {
        Assert.assertTrue(homePage.pageLoadedWithNoErrors());
        Assert.assertTrue(homePage.homePageIsDisplayed());
        homePage.clickClosePopup()
                .click_BirthDayGifts_Link();
        Verifications.verifyTrue(birthDayGiftsPage.isBirthDayGiftsDisplayed(), "Verify birthday gifts list appears");
        birthDayGiftsPage.selectGift(readFromThisFile("giftCardName")); ///select bymechef
        Verifications.verifyTrue(couponPage.getTextDisplayedGiftCard().toLowerCase().contains(readFromThisFile("giftCardName")), "Verify that the expected gift card appears after been selected ");
        Verifications.verifyTrue(couponPage.isAmountInputVisible(), "Verify amount input appears");
        Verifications.verifyTrue(couponPage.isAmountInputEnabled(), "Verift amount input is enabled");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Select gift without enter amount and verify that expected error message appears")
    public void test01_Verify_That_Amount_Field_IsMandatory() throws ParserConfigurationException, IOException, SAXException {
        couponPage.clickSelect();
        Verifications.verifyTextEquals(couponPage.getTextNoAmountEnteredErrorMessage(), readFromThisFile("CouponPage_NoAmountEntered_Error"), "Error message-No amount entered");
        Verifications.verifyTextColorIsRed(couponPage.getColorNoamountEnteredErrorMessage(), "Error message-No amount entered");
        Verifications.verifyTrue(couponPage.isErrorMessageLocatedBelowInputField(), "Verify -Error message located below amount input field");
        Verifications.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Select gift for someone else and verify next step is enabled after required fields are filled.")
    public void test02_Buy_Gift_For_Friend() throws ParserConfigurationException, IOException, SAXException {
        couponPage.sendKeysAmount(readFromThisFile("couponAmount"))
                .clickSelect();
        Assert.assertTrue(purchaseGiftCard_Step1_Page.isPurchasePageStep1Opened());
        Verifications.verifyTrue(purchaseGiftCard_Step1_Page.areAllExpectedElementsDisplayed(), "Verify all mandatory input fields appears");
        purchaseGiftCard_Step1_Page.sendKey_GiftReciever_Name(readFromThisFile("giftRecieverName"))
                .select_CelebrationReason("לבית חדש")
                .sendKey_Blessing(readFromThisFile("blessing"))
                .sendKeys_addPicOrVideo(readFromThisFile("picToLoadPath"))
                .click_Continue();
        Verifications.verifyTrue(purchaseGiftCard_Step2_Page.isPurchasePageStep2HowToSendOpened(), "Verify -Step 2 -How to send Opens");
        Verifications.assertAll();

    }

    @Test
    @Severity(SeverityLevel.TRIVIAL)
    @Description("Select the option gift for myself  and verify that the correct message appears")
    public void test03_Buy_Gift_ForMyself() throws ParserConfigurationException, IOException, SAXException {
        couponPage.sendKeysAmount(readFromThisFile("couponAmount"))
                .clickSelect();
        Assert.assertTrue(purchaseGiftCard_Step1_Page.isPurchasePageStep1Opened());
        purchaseGiftCard_Step1_Page.click_For_Myself();
        Verifications.verifyTextEquals(purchaseGiftCard_Step1_Page.getTextgiftCardForMyselfMessage(), readFromThisFile("giftForMySelf_Message"), "Text title-gift card for myself");
        Verifications.assertAll();
    }
}
