package flows;

import org.xml.sax.SAXException;
import page_Objects.*;
import test.BaseTest;
import verifications.Verifications;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.Utilities.generateRandomString;
import static utils.Utilities.readFromThisFile;

public class RegistrationAndLoginFlow extends BaseTest {
    private RegistrationPage_Step1_InsertEmail registrationPage_Step1_InsertEmail=new RegistrationPage_Step1_InsertEmail(getDriver());
    private RegistrationPage_Step2_InsertCode registrationPage_step2_insertCode=new RegistrationPage_Step2_InsertCode(getDriver());
    private RegistrationPage_Form registrationPage_form=new RegistrationPage_Form(getDriver());
    private MailiNator_Page_NewTab mailiNator_Page_NewTab=new MailiNator_Page_NewTab(getDriver());
    private HomePage homePage=new HomePage(getDriver());
    private MyAccountDetails_Page myAccountDetails_page =new MyAccountDetails_Page(getDriver());



    public RegistrationAndLoginFlow submitEmailAndSendOtp(String randomEmail) throws ParserConfigurationException, IOException, SAXException {
        registrationPage_Step1_InsertEmail.select_Register_By_Email();
        String registrationTab1_InsertEmail = getCurrentTabHandle();
        registrationPage_Step1_InsertEmail.navigateToMailiNator();
        mailiNator_Page_NewTab.createDemoEmail(randomEmail);
        String mailinatorTab = getCurrentTabHandle();
        switchToTab(registrationTab1_InsertEmail);
        registrationPage_Step1_InsertEmail.enter_By_Email(randomEmail + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        switchToTab(mailinatorTab);
        mailiNator_Page_NewTab.getFirstRecievedEmail();
        String otpCode = mailiNator_Page_NewTab.getPasswordText();
        closeCurrentTabAndSwitchTo(registrationTab2_InsertPassword);
        registrationPage_step2_insertCode.send_OtpCode(otpCode) ///Send Otp code ,enter and then fill the form and click on the register button
                .clickEnter_AfterRegisterProcess();
        return this;
    }

    public RegistrationAndLoginFlow fillRegistrationFormWithInvalidData_Flow() throws ParserConfigurationException, IOException, SAXException {
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
        return this;
    }

    public RegistrationAndLoginFlow fillRegistrationFormWithValidData_Flow() throws ParserConfigurationException, IOException, SAXException {
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
        return this;
    }

    public RegistrationAndLoginFlow loginWitRegisteredEmail_Flow(String randomEmail) throws ParserConfigurationException, IOException, SAXException {
        registrationPage_Step1_InsertEmail.select_Register_By_Email()
                .enter_By_Email(randomEmail + readFromThisFile("mailiNator_Annotation"));
        String registrationTab2_InsertPassword = getCurrentTabHandle();
        registrationPage_Step1_InsertEmail.navigateToMailiNator();
        mailiNator_Page_NewTab.sendKeysSearchEmail(randomEmail)
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
        return this;
    }

    public RegistrationAndLoginFlow insertWrongEmailFlow() throws ParserConfigurationException, IOException, SAXException {
        registrationPage_Step1_InsertEmail.select_Register_By_Email()
                .sendKeys_Email_Field(readFromThisFile("wrongEmail"));
        Verifications.verifyTextEquals(registrationPage_Step1_InsertEmail.getInputFieldValueEmail(), readFromThisFile("wrongEmail"), "Text appears on email field");
        registrationPage_Step1_InsertEmail.click_Enter_After_InsertEmail();
        Verifications.verifyTextEquals(registrationPage_Step1_InsertEmail.getTextInvalidEmailErrorMessage(), readFromThisFile("errorMessageWrongEmail"), "Error message-not valid email address");
        Verifications.verifyTextColorIsRed(registrationPage_Step1_InsertEmail.getColorInvalidEmailErrorMessage(), "Error message-not valid email address");
        Verifications.assertAll();
        return this;
    }


}
