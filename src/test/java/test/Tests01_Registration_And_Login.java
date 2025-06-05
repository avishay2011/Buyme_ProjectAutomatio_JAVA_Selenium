package test;

import flows.RegistrationAndLoginFlow;
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
    @Description("Fill registration form with invalid values and verify  the expected error messages appears ")
    public void test01_NotValidRegistration() throws ParserConfigurationException, IOException, SAXException {
        homePage.navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationAndLogin_Flow.submitEmailAndSendOtp(randomString2);
        registrationAndLogin_Flow.fillRegistrationFormWithInvalidData();
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
    public void test02_ValidRegistration() throws ParserConfigurationException, IOException, SAXException {
        homePage.navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationAndLogin_Flow.submitEmailAndSendOtp(randomString);
        registrationAndLogin_Flow.fillRegistrationFormWithValidData();
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
        registrationAndLogin_Flow.loginWitRegisteredEmail(randomString);
        Verifications.verifyTrue(homePage.homePageIsDisplayed(), "Is home page displayed");
        homePage.navigateToMyAccountDetailsPage();
        Verifications.verifyTextEquals(myAccountDetails_page.getFirstNameInputValue(), readFromThisFile("firstName"), "Text on first name field");
        Verifications.verifyTextEquals(myAccountDetails_page.getLastNameInputValue(), readFromThisFile("lastName"), "Text on last name field");
        Verifications.verifyTextEquals(myAccountDetails_page.getCellPhoneInputValue(), readFromThisFile("cellPhoneNumber") + " " + readFromThisFile("countryCode") + "+", "Text on cellphone field");
        Verifications.assertAll();
    }
}

