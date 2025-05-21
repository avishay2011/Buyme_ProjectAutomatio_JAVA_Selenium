package page_Objects;

import io.qameta.allure.Step;
import org.openqa.selenium.*;

public class MailiNator_Page_NewTab extends BasePage {

    //constructor
    public MailiNator_Page_NewTab(WebDriver driver){
        super(driver);
    }

    //elements
    private By searchEmail_Field=By.id("search");
    private By searchEmail_Button=By.cssSelector("button[value=\"Search for public inbox for free\"]");
    private By firstEmail=By.cssSelector("tr[ng-repeat=\"email in emails\"]");
    private By password=By.cssSelector(".main-card tr:nth-child(3) p");
    private By emailBodyIframe=By.id("html_msg_body");
    private By emailWithCode=By.xpath("//td[contains(text(), 'קוד')]");

    //functions

    @Step
    public MailiNator_Page_NewTab createDemoEmail(String emailAddress){
        sendKeys_SearchEmail(emailAddress);
        click__MailiNatorPage_SearchButton();
        return this;
    }

    @Step
    public MailiNator_Page_NewTab  sendKeys_SearchEmail(String emailAddress){ // It is search email field and also create a new demo email Address
        sendKeys(searchEmail_Field,emailAddress);
        return this;
    }

    @Step
    public MailiNator_Page_NewTab  click__MailiNatorPage_SearchButton(){
        click(searchEmail_Button);
        return this;
    }

    @Step
    public MailiNator_Page_NewTab  getFirst_Recieved_Email(){ // Enter the email and get the password from the email in the next step
        click(firstEmail);
        return this;
    }

    @Step
    public MailiNator_Page_NewTab getEmailContainCode(){
        click(emailWithCode);;
        return this;
    }

    @Step
    public String getPasswordText(){
       WebElement emailBody_iframe=driver.findElement(emailBodyIframe);
       driver.switchTo().frame(emailBody_iframe);
       return getText(password);
    }

}
