package page_Objects;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Coupon_Page extends BasePage {

    //Constructor
    public Coupon_Page(WebDriver driver) {
        super(driver);
    }


    //Elements
    private By giftCard_Name = By.cssSelector(".supplier-page__details h1");
    private By amount_Field = By.id("undefined-cardPrice");
    private By select_Button = By.xpath("//button[text()='בחירה']");
    private By errorMessage_NoAmountEntered = By.cssSelector(".text-field__error-message");


    //Functions

    @Step
    public Coupon_Page clickSelect() {
        click(select_Button);
        return this;
    }

    @Step
    @Description("Send keys  :  {couponAmount}")
    public Coupon_Page sendKeysAmount(String couponAmount)  {
        sendKeys(amount_Field,couponAmount);
        return this;
    }

    public String getTextDisplayedGiftCard(){
        return getText(giftCard_Name);
    }


    @Step
    public boolean isAmountInputVisible() {
        return  isElementDisplayed(amount_Field);
    }

    @Step
    public boolean isAmountInputEnabled(){
        return waitVisibility(amount_Field).isEnabled();
    }


    @Step
    public String getTextNoAmountEnteredErrorMessage(){
        return getText(errorMessage_NoAmountEntered);
    }


    @Step
    public String getColorNoamountEnteredErrorMessage()  {
        return getColor(errorMessage_NoAmountEntered);
    }

    @Step("Verify that error message is displayed *below* the input field") //להעביר ל BASE
    ////lower y value means that the element located actually higher on the screen
    public boolean isErrorMessageLocatedBelowInputField() {
            return  isElement1LocatedBelowElement2(amount_Field,errorMessage_NoAmountEntered);
  }
}
