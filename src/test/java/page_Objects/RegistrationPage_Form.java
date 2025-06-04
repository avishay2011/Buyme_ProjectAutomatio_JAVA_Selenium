
package page_Objects;


import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import static utils.Utilities.readFromThisFile;


public class RegistrationPage_Form extends BasePage {

    //constructor
    public RegistrationPage_Form(WebDriver driver){
        super(driver);
    }

    //elements
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


    @Step ("Send keys '{firstName}+\" \"+ {lastName}' on {name_Field}")
    public RegistrationPage_Form sendKeys_FullName(String firstName, String lastName) throws ParserConfigurationException, IOException, SAXException {
        sendKeys(name_Field,firstName+" "+lastName);
        return this;
    }

    @Step("Send keys '{cellPhone}' on {cellPhoneNum_Field}")
    public RegistrationPage_Form sendKeys_CellPhoneNumber(String cellPhone){
        sendKeys(cellPhoneNum_Field,cellPhone );
        return this;
    }

    @Step("Select country code from dropdown list")
    @Description("Select country code   ")
    public RegistrationPage_Form select_CountryCode(String countryCode){
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
    public RegistrationPage_Form click_IAgree_PrivacyPolicy(){
        click(iAgree_CheckBox);
        return this;
    }

    @Step("Click - \"Register\" - button")
    public RegistrationPage_Form click_Register_Button(){
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

}
