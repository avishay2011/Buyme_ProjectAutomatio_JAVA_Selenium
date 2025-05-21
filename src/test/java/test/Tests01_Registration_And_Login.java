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
import static utils.Utilities.generateRandomString;
import static utils.Utilities.readFromThisFile;

@Listeners(utils.Listeners.class)
public class

Tests01_Registration_And_Login extends BaseTest {
    private String randomString = generateRandomString(); //Generate random string that used me for registration and also for the login  test
    private String randomString2=generateRandomString();//Generate random string that used me for registration with wrong details (test04)

    @BeforeMethod
    @Severity(SeverityLevel.BLOCKER)
    public void checkPageReady() throws ParserConfigurationException, IOException, SAXException {
        Assert.assertTrue(homePage.pageLoadedWithNoErrors());
        Assert.assertTrue(homePage.homePageIsDisplayed());
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Start the registration process .get to the registration form and verify that the correct error messages appears after insert wrong values")
    public void test01_NotValidRegistration() throws ParserConfigurationException, IOException, SAXException {
        homePage.navigateTo_Registration_Page();
        Assert.assertTrue(registration_Page.registrationPageIsDisplayed());
        registration_Page.select_Register_By_Email();
        String registrationTab1_InsertEmail = getCurrentTabHandle();
        registration_Page.navigateToMailiNator();
        mailiNator_Page_NewTab.createDemoEmail(randomString2);
        String mailinatorTab = getCurrentTabHandle();
        switchToTab(registrationTab1_InsertEmail);
        registration_Page.enter_By_Email(randomString2 + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        switchToTab(mailinatorTab);
        mailiNator_Page_NewTab.getFirstRecievedEmail();
        String otpCode = mailiNator_Page_NewTab.getPasswordText();
        closeCurrentTabAndSwitchTo(registrationTab2_InsertPassword);
        registration_Page.send_OtpCode(otpCode);
        registration_Page.clickEnter_AfterRegisterProcess();
        registration_Page.sendKeys_FullName(readFromThisFile("wrongInputFirstName"), readFromThisFile("wrongInputLastName"));
        registration_Page.select_CountryCode(readFromThisFile("countryCode"));
        registration_Page.sendKeys_CellPhoneNumber(readFromThisFile("wrongInputCellPhoneNumber"));
        registration_Page.click_Register_Button();
        Verifications.verifyTextEquals(registration_Page.getTextWrongNameErrorMessage(),readFromThisFile("errorMessageWrongName"),"Not valid name-error message");
        Verifications.verifyTextColorIsRed(registration_Page.getColorWrongNameErrorMessage(),"Not valid name-error message");
        Verifications.verifyTextEquals(registration_Page.getTextWrongCellPhoneErrorMessage(),readFromThisFile("errorMessageWrongCellPhoneNumber"),"Not valid cellphone-error message");
        Verifications.verifyTextColorIsRed(registration_Page.getColorWrongCellPhoneErrorMessage(),"Not valid cellphone-error message");
        Verifications.verifyTextEquals(registration_Page.getTextiAgreeNotCheckedErrorMessage().replaceAll("\\s+", " ").trim(),readFromThisFile("errorMessageIagreeNotChecked").replaceAll("\\s+", " ").trim(),"I agree-not checked-error message");
        Verifications.verifyTextColorIsRed(registration_Page.getColoriAgreeNotCheckedErrorMessage(),"I agree-not checked-error message");
        Verifications.assertAll();

    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete the registration process with valid inputs and verify that log in made with the new account, and check that the account details match the inputs that provided   ")
    public void test02_Registration() throws ParserConfigurationException, IOException, SAXException {
        homePage.navigateTo_Registration_Page();
        Assert.assertTrue(registration_Page.registrationPageIsDisplayed());
        registration_Page.select_Register_By_Email();
        String registrationTab1_InsertEmail = getCurrentTabHandle();
        registration_Page.navigateToMailiNator();
        mailiNator_Page_NewTab.createDemoEmail(randomString);
        String mailinatorTab = getCurrentTabHandle();
        switchToTab(registrationTab1_InsertEmail);
        registration_Page.enter_By_Email(randomString + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        switchToTab(mailinatorTab);
        mailiNator_Page_NewTab.getFirstRecievedEmail();
        String otpCode = mailiNator_Page_NewTab.getPasswordText();
        closeCurrentTabAndSwitchTo(registrationTab2_InsertPassword);
        registration_Page.send_OtpCode(otpCode);
        registration_Page.clickEnter_AfterRegisterProcess();
        registration_Page.sendKeys_FullName(readFromThisFile("firstName"), readFromThisFile("lastName"));
        registration_Page.select_CountryCode(readFromThisFile("countryCode"));
        registration_Page.sendKeys_CellPhoneNumber(readFromThisFile("cellPhoneNumber"));
        registration_Page.click_IAgree_PrivacyPolicy();
        registration_Page.click_Register_Button();
        homePage.navigateTo_MyAccountDetails_Page();
        Verifications.verifyTextEquals(myAccountDetails_page.getFirstNameInputValue(),readFromThisFile("firstName"),"Text appears on firstName field");
        Verifications.verifyTextEquals(myAccountDetails_page.getLastNameInputValue(),readFromThisFile( "lastName"), "Text appears on firstName field");
        Verifications.verifyTextEquals(myAccountDetails_page.getCellPhoneInputValue(),readFromThisFile("cellPhoneNumber")+" "+readFromThisFile("countryCode")+"+","Text appears on cellphone field");
        Verifications.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with not valid email and verify that expected error message appears ")
    public void test03_ErrorMessage_Not_Valid_Email() throws ParserConfigurationException, IOException, SAXException {
        homePage.logOut();
        homePage.navigateTo_Registration_Page();
        Assert.assertTrue(registration_Page.registrationPageIsDisplayed());
        registration_Page.select_Register_By_Email();
        registration_Page.sendKeys_Email_Field(readFromThisFile("wrongEmail"));
        Verifications.verifyTextEquals(registration_Page.getInputFieldValueEmail(),readFromThisFile("wrongEmail"),"Text appears on email field");
        registration_Page.click_Enter_After_InsertEmail();
        Verifications.verifyTextEquals(registration_Page.getTextInvalidEmailErrorMessage(),readFromThisFile("errorMessageWrongEmail"),"Error message-not valid email address");
        Verifications.verifyTextColorIsRed(registration_Page.getColorInvalidErrorMessage(),"Error message-not valid email address");
        Verifications.assertAll();
    }



    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Entered to web with a user that allready registered and verify that login made after got otp code ")
    public void test04_Login_with_registered_email() throws ParserConfigurationException, IOException, SAXException {
        homePage.logOut();
        homePage.navigateTo_Registration_Page();
        Assert.assertTrue(registration_Page.registrationPageIsDisplayed());
        registration_Page.select_Register_By_Email();
        registration_Page.enter_By_Email(randomString + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        registration_Page.navigateToMailiNator();
        mailiNator_Page_NewTab.sendKeysSearchEmail(randomString);
        mailiNator_Page_NewTab.clickMailiNatorPageSearchButton();
        mailiNator_Page_NewTab.getEmailContainCode();
        String otpCode = mailiNator_Page_NewTab.getPasswordText();
        closeCurrentTabAndSwitchTo(registrationTab2_InsertPassword);
        registration_Page.send_OtpCode(otpCode);
        registration_Page.clickEnter_AfterLoginProcess();
        registration_Page.verify_SMS_OtpCode();
        Verifications.verifyTrue(homePage.homePageIsDisplayed(),"Is home page displayed");
        homePage.navigateTo_MyAccountDetails_Page();
        Verifications.verifyTextEquals(myAccountDetails_page.getFirstNameInputValue(),readFromThisFile("firstName"),"Text on first name field");
        Verifications.verifyTextEquals(myAccountDetails_page.getLastNameInputValue(),readFromThisFile( "lastName"), "Text on last name field");
        Verifications.verifyTextEquals(myAccountDetails_page.getCellPhoneInputValue(),readFromThisFile("cellPhoneNumber")+" "+readFromThisFile("countryCode")+"+","Text on cellphone field");
        Verifications.assertAll();
    }



}

