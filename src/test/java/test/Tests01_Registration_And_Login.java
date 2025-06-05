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
        registrationAndLogin_Flow.fillRegistrationFormWithInvalidData_Flow();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Complete the registration process with valid inputs and verify that log in made with the new account, and check that the account details match the inputs that provided   ")
    public void test02_ValidRegistration() throws ParserConfigurationException, IOException, SAXException {
        homePage.navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationAndLogin_Flow.submitEmailAndSendOtp(randomString);
        registrationAndLogin_Flow.fillRegistrationFormWithValidData_Flow();
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Login with not valid email and verify that expected error message appears ")
    public void test03_ErrorMessage_Not_Valid_Email() throws ParserConfigurationException, IOException, SAXException {
        homePage.logOut()
                .navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationAndLogin_Flow.insertWrongEmailFlow();
    }


    @Test
    @Severity(SeverityLevel.CRITICAL)
    @Description("Entered to web with a user that allready registered and verify that login made after got otp code ")
    public void test04_Login_with_registered_email() throws ParserConfigurationException, IOException, SAXException {
        homePage.logOut()
                .navigateToRegistrationPage();
        Assert.assertTrue(registrationPage_Step1_InsertEmail.registrationPageIsDisplayed());
        registrationAndLogin_Flow.loginWitRegisteredEmail_Flow(randomString);
    }
}

