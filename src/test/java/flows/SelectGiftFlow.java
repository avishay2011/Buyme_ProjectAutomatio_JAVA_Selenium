package flows;

import org.testng.Assert;
import org.xml.sax.SAXException;
import page_Objects.*;
import test.BaseTest;
import verifications.Verifications;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.Utilities.readFromThisFile;

public class SelectGiftFlow extends BaseTest {
    private HomePage homePage=new HomePage(getDriver());
    private BirthDayGifts_Page birthDayGiftsPage=new BirthDayGifts_Page(getDriver());
    private Coupon_Page couponPage=new Coupon_Page(getDriver());
    private Purchase_GiftCard_Step1_Page purchaseGiftCard_Step1_Page=new Purchase_GiftCard_Step1_Page(getDriver());
    private Purchase_GiftCard_Step2_Page purchaseGiftCard_Step2_Page=new Purchase_GiftCard_Step2_Page(getDriver());

    public SelectGiftFlow giftListAmountAndCardDisplayFlow() throws ParserConfigurationException, IOException, SAXException {
        homePage.clickClosePopup()
                .click_BirthDayGifts_Link();
        Verifications.verifyTrue(birthDayGiftsPage.isBirthDayGiftsDisplayed(), "Verify birthday gifts list appears");
        birthDayGiftsPage.selectGift(readFromThisFile("giftCardName")); ///select bymechef
        Verifications.verifyTrue(couponPage.getTextDisplayedGiftCard().toLowerCase().contains(readFromThisFile("giftCardName")), "Verify that the expected gift card appears after been selected ");
        Verifications.verifyTrue(couponPage.isAmountInputVisible(), "Verify amount input appears");
        Verifications.verifyTrue(couponPage.isAmountInputEnabled(), "Verift amount input is enabled");
        return this;
    }

    public SelectGiftFlow selectNoAmountEntered_Flow() throws ParserConfigurationException, IOException, SAXException {
        couponPage.clickSelect();
        Verifications.verifyTextEquals(couponPage.getTextNoAmountEnteredErrorMessage(), readFromThisFile("CouponPage_NoAmountEntered_Error"), "Error message-No amount entered");
        Verifications.verifyTextColorIsRed(couponPage.getColorNoamountEnteredErrorMessage(), "Error message-No amount entered");
        Verifications.verifyTrue(couponPage.isErrorMessageLocatedBelowInputField(), "Verify -Error message located below amount input field");
        Verifications.assertAll();
        return this;
    }

    public SelectGiftFlow verifyMandatoryInputsAppears_Flow() throws ParserConfigurationException, IOException, SAXException {
        couponPage.sendKeysAmount(readFromThisFile("couponAmount"))
                .clickSelect();
        Assert.assertTrue(purchaseGiftCard_Step1_Page.isPurchasePageStep1Opened());
        Verifications.verifyTrue(purchaseGiftCard_Step1_Page.areAllExpectedElementsDisplayed(), "Verify all mandatory input fields appears");
        return this;
    }

    public SelectGiftFlow fillGiftCardReceiverFormFlow() throws ParserConfigurationException, IOException, SAXException {
        purchaseGiftCard_Step1_Page.sendKey_GiftReciever_Name(readFromThisFile("giftRecieverName"))
                .select_CelebrationReason("לבית חדש")
                .sendKey_Blessing(readFromThisFile("blessing"))
                .sendKeys_addPicOrVideo(readFromThisFile("picToLoadPath"))
                .click_Continue();
        Verifications.verifyTrue(purchaseGiftCard_Step2_Page.isPurchasePageStep2HowToSendOpened(), "Verify -Step 2 -How to send Opens");
        Verifications.assertAll();
        return this;
    }

    public SelectGiftFlow selectGiftForMyselfFlow() throws ParserConfigurationException, IOException, SAXException {
        couponPage.sendKeysAmount(readFromThisFile("couponAmount"))
                .clickSelect();
        Assert.assertTrue(purchaseGiftCard_Step1_Page.isPurchasePageStep1Opened());
        purchaseGiftCard_Step1_Page.click_For_Myself();
        Verifications.verifyTextEquals(purchaseGiftCard_Step1_Page.getTextgiftCardForMyselfMessage(), readFromThisFile("giftForMySelf_Message"), "Text title-gift card for myself");
        Verifications.assertAll();
        return this;
    }
}
