package flows;

import org.testng.Assert;
import org.xml.sax.SAXException;
import page_Objects.GiftCard_Balance_Page;
import page_Objects.HomePage;
import test.BaseTest;
import verifications.Verifications;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.HashMap;

import static utils.Utilities.readFromThisFile;

public class CheckGiftCardBalanceFlow extends BaseTest {
    private HomePage homePage=new HomePage(getDriver());
    private GiftCard_Balance_Page giftCard_balance_page=new GiftCard_Balance_Page(getDriver());

    public CheckGiftCardBalanceFlow verifyGiftCardBalanceScreenFlow() throws ParserConfigurationException, IOException, SAXException {
        homePage.clickcheckGiftCardBalanceButton();
        Assert.assertTrue(giftCard_balance_page.isGiftBalanceScreenOpen());
        Verifications.verifyTextEquals(giftCard_balance_page.getText_WhatIsYourCodeMessageText(), readFromThisFile("whatisCode_Text"), "Text near code field");
        Verifications.verifyTextEquals(giftCard_balance_page.getText_WhatIsExpirationDate_MessageText(), readFromThisFile("whatisExpiration_Text"), "Text near expiration field");
        return this;
    }

    public CheckGiftCardBalanceFlow fillGiftCardDetails_WrongGiftCardCode() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        giftCard_balance_page.sendKeys_CouponField(readFromThisFile("wrongcouponCode"))
                .click_DatePicker_Button()
                .select_Date_Picker(readFromThisFile("expirationDay"), readFromThisFile("expirationMonth"), readFromThisFile("expirationYear"))
                .click_CheckBalance_Button();
        Verifications.verifyTextEquals(giftCard_balance_page.getText_WrongGiftCardCode_ErrorMessage(), readFromThisFile("wrongGiftCardCode_ErrorMessage"), "Error message -Wrong gift card code");
        Verifications.verifyTextColorIsRed(giftCard_balance_page.getColor_WrongGiftCardCode_ErrorMessage(), "Error message -Wrong gift card code");
        Verifications.assertAll();
        return this;
    }

    public CheckGiftCardBalanceFlow fillGiftCardDetails_UsedGiftCardCode() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        giftCard_balance_page.sendKeys_CouponField(readFromThisFile("existsUsedCouponCode"))
                .click_DatePicker_Button()
                .select_Date_Picker(readFromThisFile("existsUsedCouponExpiration_day"), readFromThisFile("existsUsedCouponExpiration_month"), readFromThisFile("existsUsedCouponExpiration_year"))
                .click_CheckBalance_Button();
        Verifications.verifyTextEquals(giftCard_balance_page.getText_UsedGiftCard_BalanceMessage(), readFromThisFile("giftCardAllreadyUsed_BalanceMessage"), "Gift card balance message");
        Verifications.assertAll();
        return this;
    }

    public CheckGiftCardBalanceFlow fillGiftCardDetails_ValidGiftCardCode() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        giftCard_balance_page.sendKeys_CouponField(readFromThisFile("validCouponCode"))
                .click_DatePicker_Button()
                .select_Date_Picker(readFromThisFile("validCouponExpiration_day"), readFromThisFile("validCouponExpiration_month"), readFromThisFile("validCouponExpiration_year"))
                .click_CheckBalance_Button();
        Verifications.verifyTrue(giftCard_balance_page.getText_Valid_GiftCardBalance_Message().contains(readFromThisFile("giftCard_Valid_Balance")), "Verify Text contains expected balance");
        Verifications.assertAll();
        return this;
    }


}
