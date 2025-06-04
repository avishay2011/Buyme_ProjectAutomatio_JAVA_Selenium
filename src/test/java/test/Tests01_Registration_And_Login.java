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
    private String randomString2 = generateRandomString();//Generate random string that used me for registration with wrong details (test04)

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
        homePage.navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationPage_Step1_InsertEmail.select_Register_By_Email();
        String registrationTab1_InsertEmail = getCurrentTabHandle();
        registrationPage_Step1_InsertEmail.navigateToMailiNator();
        mailiNator_Page_NewTab.createDemoEmail(randomString2);
        String mailinatorTab = getCurrentTabHandle();
        switchToTab(registrationTab1_InsertEmail);
        registrationPage_Step1_InsertEmail.enter_By_Email(randomString2 + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        switchToTab(mailinatorTab);
        mailiNator_Page_NewTab.getFirstRecievedEmail();
        String otpCode = mailiNator_Page_NewTab.getPasswordText();
        closeCurrentTabAndSwitchTo(registrationTab2_InsertPassword);
        registrationPage_step2_insertCode.send_OtpCode(otpCode) ///Send Otp code ,enter and then fill the form and click on the register button
                .clickEnter_AfterRegisterProcess();
        registrationPage_form.sendKeys_FullName(readFromThisFile("wrongInputFirstName"), readFromThisFile("wrongInputLastName"))
                .select_CountryCode(readFromThisFile("countryCode"))
                .sendKeys_CellPhoneNumber(readFromThisFile("wrongInputCellPhoneNumber"))
                .click_Register_Button();
        Verifications.verifyTextEquals(registrationPage_form.getTextWrongNameErrorMessage(), readFromThisFile("errorMessageWrongName"), "Not valid name-error message");
        Verifications.verifyTextColorIsRed(registrationPage_form.getColorWrongNameErrorMessage(), "Not valid name-error message");
        Verifications.verifyTextEquals(registrationPage_form.getTextWrongCellPhoneErrorMessage(), readFromThisFile("errorMessageWrongCellPhoneNumber"), "Not valid cellphone-error message");
        Verifications.verifyTextColorIsRed(registrationPage_form.getColorWrongCellPhoneErrorMessage(), "Not valid cellphone-error message");
        Verifications.verifyTextEquals(registrationPage_form.getTextiAgreeNotCheckedErrorMessage().replaceAll("\\s+", " ").trim(), readFromThisFile("errorMessageIagreeNotChecked").replaceAll("\\s+", " ").trim(), "I agree-not checked-error message");
        Verifications.verifyTextColorIsRed(registrationPage_form.getColoriAgreeNotCheckedErrorMessage(), "I agree-not checked-error message");
        Verifications.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete the registration process with valid inputs and verify that log in made with the new account, and check that the account details match the inputs that provided   ")
    public void test02_Registration() throws ParserConfigurationException, IOException, SAXException {
        homePage.navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationPage_Step1_InsertEmail.select_Register_By_Email();
        String registrationTab1_InsertEmail = getCurrentTabHandle();
        registrationPage_Step1_InsertEmail.navigateToMailiNator();
        mailiNator_Page_NewTab.createDemoEmail(randomString);
        String mailinatorTab = getCurrentTabHandle();
        switchToTab(registrationTab1_InsertEmail);
        registrationPage_Step1_InsertEmail.enter_By_Email(randomString + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        switchToTab(mailinatorTab);
        mailiNator_Page_NewTab.getFirstRecievedEmail();
        String otpCode = mailiNator_Page_NewTab.getPasswordText();
        closeCurrentTabAndSwitchTo(registrationTab2_InsertPassword);
        registrationPage_step2_insertCode.send_OtpCode(otpCode) //////Send Otp code ,enter and then fill the form and click on the register button
                .clickEnter_AfterRegisterProcess();
        registrationPage_form.sendKeys_FullName(readFromThisFile("firstName"), readFromThisFile("lastName"))
                .select_CountryCode(readFromThisFile("countryCode"))
                .sendKeys_CellPhoneNumber(readFromThisFile("cellPhoneNumber"))
                .click_IAgree_PrivacyPolicy()
                .click_Register_Button();
        homePage.navigateToMyAccountDetailsPage();
        Verifications.verifyTextEquals(myAccountDetails_page.getFirstNameInputValue(), readFromThisFile("firstName"), "FirstName input  field text");
        Verifications.verifyTextEquals(myAccountDetails_page.getLastNameInputValue(), readFromThisFile("lastName"), "Last name input field text");
        Verifications.verifyTextEquals(myAccountDetails_page.getCellPhoneInputValue(), readFromThisFile("cellPhoneNumber") + " " + readFromThisFile("countryCode") + "+", "Cell phone input field text");
        Verifications.assertAll();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with not valid email and verify that expected error message appears ")
    public void test03_ErrorMessage_Not_Valid_Email() throws ParserConfigurationException, IOException, SAXException {
        homePage.logOut()
                .navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationPage_Step1_InsertEmail.select_Register_By_Email()
                .sendKeys_Email_Field(readFromThisFile("wrongEmail"));
        Verifications.verifyTextEquals(registrationPage_Step1_InsertEmail.getInputFieldValueEmail(), readFromThisFile("wrongEmail"), "Text appears on email field");
        registrationPage_Step1_InsertEmail.click_Enter_After_InsertEmail();
        Verifications.verifyTextEquals(registrationPage_Step1_InsertEmail.getTextInvalidEmailErrorMessage(), readFromThisFile("errorMessageWrongEmail"), "Error message-not valid email address");
        Verifications.verifyTextColorIsRed(registrationPage_Step1_InsertEmail.getColorInvalidEmailErrorMessage(), "Error message-not valid email address");
        Verifications.assertAll();
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Entered to web with a user that allready registered and verify that login made after got otp code ")
    public void test04_Login_with_registered_email() throws ParserConfigurationException, IOException, SAXException {
        homePage.logOut()
                .navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationPage_Step1_InsertEmail.select_Register_By_Email()
                .enter_By_Email(randomString + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        registrationPage_Step1_InsertEmail.navigateToMailiNator();
        mailiNator_Page_NewTab.sendKeysSearchEmail(randomString)
                .clickMailiNatorPageSearchButton()
                .getEmailContainCode();
        String otpCode = mailiNator_Page_NewTab.getPasswordText();
        closeCurrentTabAndSwitchTo(registrationTab2_InsertPassword);
        registrationPage_step2_insertCode.send_OtpCode(otpCode)
                .clickEnter_AfterLoginProcess()
                .verify_SMS_OtpCode();
        Verifications.verifyTrue(homePage.homePageIsDisplayed(), "Is home page displayed");
        homePage.navigateToMyAccountDetailsPage();
        Verifications.verifyTextEquals(myAccountDetails_page.getFirstNameInputValue(), readFromThisFile("firstName"), "Text on first name field");
        Verifications.verifyTextEquals(myAccountDetails_page.getLastNameInputValue(), readFromThisFile("lastName"), "Text on last name field");
        Verifications.verifyTextEquals(myAccountDetails_page.getCellPhoneInputValue(), readFromThisFile("cellPhoneNumber") + " " + readFromThisFile("countryCode") + "+", "Text on cellphone field");
        Verifications.assertAll();
    }
}

