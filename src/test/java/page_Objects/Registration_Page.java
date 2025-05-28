
package page_Objects;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static utils.Utilities.readFromThisFile;


public class Registration_Page extends BasePage {

    //constructor
    public Registration_Page(WebDriver driver){
        super(driver);
    }

    //elements
    private By login_ByEmail_Button=By.cssSelector(".social-btn:nth-child(2)");
    private By email_Field=By.cssSelector(".login-form input[type=\"email\"]");
    private By logInByGoogle_Link=By.cssSelector(".social-btn.google");/// Unique element that used only for verify that the registration page opened-
    private By enter_Button=By.cssSelector("button[gtm=\"כניסה\"]");
    private By errorMessage_WrongEmail=By.cssSelector(".parsley-errors-list.filled>li");
    private By otpCode_Field_Location=By.id("otp-code");
    private By verifyEmail_OtpCode_Button=By.cssSelector("button[gtm=\"אימות מייל\"]");///button appears in registration process
    private By verifySMS_OtpCode_Button=By.cssSelector("button[gtm=\"שלחו לי קוד אימות\"]");
    private By enter_Button_Get_Code=By.cssSelector("button[gtm=\"כניסה\"]");  ///button appears in login process
    private By name_Field=By.cssSelector("input[placeholder=\"שם מלא\"]");
    private By countriesCodes_Button=By.cssSelector(".selected>span>svg");//used for open the countries codes list
    private By cellPhoneNum_Field=By.cssSelector("input[type=\"tel\"]");
    private By iAgree_CheckBox=By.cssSelector(".fill");
    private By register_button=By.cssSelector("form>button[type=\"submit\"]");
    private By countriesCode_List_Location=By.cssSelector(".box-search>.list>.row>span:nth-child(3)");
    private By errorMessage_WrongName=By.xpath("//li[contains(text(),'שם מלא')]");
    private By errorMessageCellPhoneNumber=By.xpath("//li[contains(text(),'המספר שהזנת')]");
    private By radioButtonNotChecked_ErrorMessage=By.xpath("//li[contains(text(),'עליך לאשר את תנאי')]");



    // =============== Actions =====================
    // =============================================


    @Step("Select - register by email")
    public Registration_Page select_Register_By_Email(){
        click(login_ByEmail_Button);
        return this;
    }


    @Step("Insert Email and click enter")
    public Registration_Page enter_By_Email(String email_Field){
        sendKeys_Email_Field(email_Field);
        click_Enter_After_InsertEmail();
        return this;
    }


    @Step("Send keys '{email}' on {email_Field}")
    public Registration_Page sendKeys_Email_Field(String email){
        sendKeys(email_Field,email);
        return this;
    }

    @Step("Click enter button ")
    public Registration_Page click_Enter_After_InsertEmail(){
        click(enter_Button);
        return this;
    }


    @Step("Navigate to mailinator page - for get a password or create a new user  ")
    public Registration_Page navigateToMailiNator() throws ParserConfigurationException, IOException, SAXException { /// Using the service of mailinator for create demo email address and sent it for verificatin on buyme registration proccess
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(readFromThisFile("mailiNatorurl"));
        return this;
    }

    @Step("Send keys '{otpCode}' on {otpCode_Field_Location}")
    public Registration_Page send_OtpCode(String otpCode){
        sendKeys(otpCode_Field_Location,otpCode);
        return this;
    }

    @Step("Verify Otp code -click verify SMS ")
    public Registration_Page verify_SMS_OtpCode(){
        click(verifySMS_OtpCode_Button);
        return this;
    }

    @Step("Click Enter ")
    public Registration_Page clickEnter_AfterRegisterProcess(){
        click(verifyEmail_OtpCode_Button);
        return this;
    }

    @Step("Click Enter ")
    public Registration_Page clickEnter_AfterLoginProcess(){
        click(enter_Button_Get_Code);
        return this;
    }


    @Step ("Send keys '{firstName}+\" \"+ {lastName}' on {name_Field}")
    public Registration_Page sendKeys_FullName(String firstName,String lastName) throws ParserConfigurationException, IOException, SAXException {
        sendKeys(name_Field,firstName+" "+lastName);
        return this;
    }

    @Step("Send keys '{cellPhone}' on {cellPhoneNum_Field}")
    public Registration_Page sendKeys_CellPhoneNumber(String cellPhone){
        sendKeys(cellPhoneNum_Field,cellPhone );
        return this;
    }

    @Step("Select country code from dropdown list")
    @Description("Select country code   ")
    public Registration_Page select_CountryCode(String countryCode){
        click(countriesCodes_Button);
        int count=0;
        List<WebElement> countriesCodes_List=getElementsFromListLocation(countriesCode_List_Location);
        for (WebElement elem: countriesCodes_List){
            if(elem.getText().equals("+"+countryCode)){
               elem.click();
               break;
            }
        }
        return this;
    }


    @Step("Click \"I agree\" -radion button")
    public Registration_Page click_IAgree_PrivacyPolicy(){
        click(iAgree_CheckBox);
        return this;
    }

    @Step("Click - \"Register\" - button")
    public Registration_Page click_Register_Button(){
        click(register_button);
        return this;
    }

    // =============== Getters and validations =====================
    // =============================================

    @Step
    public String getTextWrongNameErrorMessage(){
        return getText(errorMessage_WrongName);
    }

    public @Step String getColorWrongNameErrorMessage(){
        return getColor(errorMessage_WrongName);
    }

    @Step
    public String getTextWrongCellPhoneErrorMessage(){
        return getText(errorMessageCellPhoneNumber);
    }

    @Step
    public String getColorWrongCellPhoneErrorMessage(){
        return getColor(errorMessageCellPhoneNumber);
    }

    @Step
    public String getTextiAgreeNotCheckedErrorMessage(){
        return getText(radioButtonNotChecked_ErrorMessage);
    }

    @Step
    public String getColoriAgreeNotCheckedErrorMessage(){
        return getColor(radioButtonNotChecked_ErrorMessage);
    }



    @Step
    public String getInputFieldValueEmail(){
        return getInputFieldValue(email_Field);
    }

    @Step
    public String getTextInvalidEmailErrorMessage(){
        return getText(errorMessage_WrongEmail);
    }

    @Step
    public String getColorInvalidErrorMessage(){
        return getColor(errorMessage_WrongEmail);
    }

    @Step
    @Description("Verify by expected url and unique element that registration page opened ")
    public boolean registrationPageIsDisplayed() throws ParserConfigurationException, IOException, SAXException {  // verify that the right page loaded by  elem location that unique for this page
        return driver.getCurrentUrl().equalsIgnoreCase(readFromThisFile("registrationurl")) && isElementDisplayed(logInByGoogle_Link);
    }
}
