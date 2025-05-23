package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
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
        homePage.clickcheckGiftCardBalanceButton();
        Assert.assertTrue(giftCard_balance_page.isGiftBalanceScreenOpen());
        Verifications.verifyTextEquals(giftCard_balance_page.getText_WhatIsYourCodeMessageText(), readFromThisFile("whatisCode_Text"),"Text for code field");
        Verifications.verifyTextEquals(giftCard_balance_page.getText_WhatIsExpirationDate_MessageText(), readFromThisFile("whatisExpiration_Text"),"Text for expiration field");
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Check not exists gift card Code and verify that the correct error message appears")
    public void test01_ErrorShown_ForNonExistentGiftCardCode() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        giftCard_balance_page.sendKeys_CouponField(readFromThisFile("couponCode"));
        giftCard_balance_page.click_DatePicker_Button();
        giftCard_balance_page.select_Date_Picker(readFromThisFile("expirationDay"), readFromThisFile("expirationMonth"), readFromThisFile("expirationYear"));
        giftCard_balance_page.click_CheckBalance_Button();
        Verifications.verifyTextEquals(giftCard_balance_page.getText_WrongGiftCardCode_ErrorMessage(), readFromThisFile("wrongGiftCardCode_ErrorMessage"),"Error message -Wrong gift card code");
        Verifications.verifyTextColorIsRed(giftCard_balance_page.getColor_WrongGiftCardCode_ErrorMessage(),"Error message -Wrong gift card code");
        Verifications.assertAll();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Check exists gift card Code that allready used and verify that the correct message appears")
    /////Was supposed to check an existing expired voucher, but since I didn’t have one – I checked a voucher that was already fully used instead
    public void test02_ErrorShown_ForExistentGiftCardCode_ThatAllreadyUsed() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        giftCard_balance_page.sendKeys_CouponField(readFromThisFile("existsUsedCouponCode"));
        giftCard_balance_page.click_DatePicker_Button();
        giftCard_balance_page.select_Date_Picker(readFromThisFile("existsUsedCouponExpiration_day"), readFromThisFile("existsUsedCouponExpiration_month"), readFromThisFile("existsUsedCouponExpiration_year"));
        giftCard_balance_page.click_CheckBalance_Button();
        Verifications.verifyTextEquals(giftCard_balance_page.getText_UsedGiftCard_BalanceMessage(), readFromThisFile("giftCardAllreadyUsed_BalanceMessage"),"Gift card balance message");
        Verifications.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Check valid gift card code and verify that the right message appears with the current balanced")
    public void test03_ValidGiftCard() throws ParserConfigurationException, IOException, SAXException, InterruptedException {
        giftCard_balance_page.sendKeys_CouponField(readFromThisFile("validCouponCode"));
        giftCard_balance_page.click_DatePicker_Button();
        giftCard_balance_page.select_Date_Picker(readFromThisFile("validCouponExpiration_day"), readFromThisFile("validCouponExpiration_month"), readFromThisFile("validCouponExpiration_year"));
        giftCard_balance_page.click_CheckBalance_Button();
        Verifications.verifyTrue(giftCard_balance_page.getText_Valid_GiftCardBalance_Message().contains(readFromThisFile("giftCard_Valid_Balance")),"Verify Text contains expected balance");
        Verifications.assertAll();
    }

}