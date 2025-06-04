
package page_Objects;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import static utils.Utilities.readFromThisFile;


public class RegistrationPage_Step2_InsertCode extends BasePage {

    //constructor
    public RegistrationPage_Step2_InsertCode(WebDriver driver){
        super(driver);
    }

    //elements

    private By otpCode_Field_Location=By.id("otp-code");
    private By verifyEmail_OtpCode_Button=By.cssSelector("button[gtm=\"אימות מייל\"]");///button appears in registration process
    private By verifySMS_OtpCode_Button=By.cssSelector("button[gtm=\"שלחו לי קוד אימות\"]");
    private By enter_Button_After_Get_Code=By.cssSelector("button[gtm=\"כניסה\"]");  ///button appears in login process


    // =============== Actions =====================
    // =============================================


    @Step("Send keys '{otpCode}' on {otpCode_Field_Location}")
    public RegistrationPage_Step2_InsertCode send_OtpCode(String otpCode){
        sendKeys(otpCode_Field_Location,otpCode);
        return this;
    }

    @Step("Verify Otp code -click verify SMS ")
    public RegistrationPage_Step2_InsertCode verify_SMS_OtpCode(){
        click(verifySMS_OtpCode_Button);
        return this;
    }

    @Step("Click Enter ")
    public RegistrationPage_Step2_InsertCode clickEnter_AfterRegisterProcess(){
        click(verifyEmail_OtpCode_Button);
        return this;
    }

    @Step("Click Enter ")
    public RegistrationPage_Step2_InsertCode clickEnter_AfterLoginProcess(){
        click(enter_Button_After_Get_Code);
        return this;
    }














}
