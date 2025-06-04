
package page_Objects;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import static utils.Utilities.readFromThisFile;


public class RegistrationPage_Step1_InsertEmail extends BasePage {

    //constructor
    public RegistrationPage_Step1_InsertEmail(WebDriver driver){
        super(driver);
    }

    //elements
    private By login_ByEmail_Button=By.cssSelector(".social-btn:nth-child(2)");
    private By email_Field=By.cssSelector(".login-form input[type=\"email\"]");
    private By logInByGoogle_Link=By.cssSelector(".social-btn.google");/// Unique element that used only for verify that the registration page opened-
    private By enter_Button=By.cssSelector("button[gtm=\"כניסה\"]");
    private By errorMessage_WrongEmail=By.cssSelector(".parsley-errors-list.filled>li");


    // =============== Actions =====================
    // =============================================


    @Step("Select - register by email")
    public RegistrationPage_Step1_InsertEmail select_Register_By_Email(){
        click(login_ByEmail_Button);
        return this;
    }


    @Step("Insert Email and click enter")
    public RegistrationPage_Step1_InsertEmail enter_By_Email(String email_Field){
        sendKeys_Email_Field(email_Field);
        click_Enter_After_InsertEmail();
        return this;
    }


    @Step("Send keys '{email}' on {email_Field}")
    public RegistrationPage_Step1_InsertEmail sendKeys_Email_Field(String email){
        sendKeys(email_Field,email);
        return this;
    }

    @Step("Click enter button ")
    public RegistrationPage_Step1_InsertEmail click_Enter_After_InsertEmail(){
        click(enter_Button);
        return this;
    }


    @Step("Navigate to mailinator page - for get a password or create a new user  ")
    public RegistrationPage_Step1_InsertEmail navigateToMailiNator() throws ParserConfigurationException, IOException, SAXException { /// Using the service of mailinator for create demo email address and sent it for verificatin on buyme registration proccess
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get(readFromThisFile("mailiNatorurl"));
        return this;
    }


    // =============== Getters and validations =====================
    // =============================================



    @Step
    public String getInputFieldValueEmail(){
        return getInputFieldValue(email_Field);
    }

    @Step
    public String getTextInvalidEmailErrorMessage(){
        return getText(errorMessage_WrongEmail);
    }

    @Step
    public String getColorInvalidEmailErrorMessage(){
        return getColor(errorMessage_WrongEmail);
    }

    @Step
    @Description("Verify by expected url and unique element that registration page opened ")
    public boolean registrationPageIsDisplayed() throws ParserConfigurationException, IOException, SAXException {  // verify that the right page loaded by  elem location that unique for this page
        return driver.getCurrentUrl().equalsIgnoreCase(readFromThisFile("registrationurl")) && isElementDisplayed(logInByGoogle_Link);
    }
}
