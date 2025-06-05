package flows;

import org.xml.sax.SAXException;
import page_Objects.MailiNator_Page_NewTab;
import page_Objects.RegistrationPage_Form;
import page_Objects.RegistrationPage_Step1_InsertEmail;
import page_Objects.RegistrationPage_Step2_InsertCode;
import test.BaseTest;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.Utilities.generateRandomString;
import static utils.Utilities.readFromThisFile;

public class RegistrationAndLoginFlow extends BaseTest {
    private RegistrationPage_Step1_InsertEmail registrationPage_Step1_InsertEmail=new RegistrationPage_Step1_InsertEmail(getDriver());
    private RegistrationPage_Step2_InsertCode registrationPage_step2_insertCode=new RegistrationPage_Step2_InsertCode(getDriver());
    private RegistrationPage_Form registrationPage_form=new RegistrationPage_Form(getDriver());
    private MailiNator_Page_NewTab mailiNator_Page_NewTab=new MailiNator_Page_NewTab(getDriver());



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

    public RegistrationAndLoginFlow fillRegistrationFormWithInvalidData() throws ParserConfigurationException, IOException, SAXException {
        registrationPage_form.sendKeys_FullName(readFromThisFile("wrongInputFirstName"), readFromThisFile("wrongInputLastName"))
                .select_CountryCode(readFromThisFile("countryCode"))
                .sendKeys_CellPhoneNumber(readFromThisFile("wrongInputCellPhoneNumber"))
                .click_Register_Button();
        return this;
    }

    public RegistrationAndLoginFlow fillRegistrationFormWithValidData() throws ParserConfigurationException, IOException, SAXException {
        registrationPage_form.sendKeys_FullName(readFromThisFile("firstName"), readFromThisFile("lastName"))
                .select_CountryCode(readFromThisFile("countryCode"))
                .sendKeys_CellPhoneNumber(readFromThisFile("cellPhoneNumber"))
                .click_IAgree_PrivacyPolicy()
                .click_Register_Button();
        return this;
    }

    public RegistrationAndLoginFlow loginWitRegisteredEmail(String randomEmail) throws ParserConfigurationException, IOException, SAXException {
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
        return this;
    }


}
