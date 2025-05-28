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

    @Step("Create demo email ")
    public MailiNator_Page_NewTab createDemoEmail(String emailAddress){
        sendKeysSearchEmail(emailAddress);
        clickMailiNatorPageSearchButton();
        return this;
    }

    @Step("Send keys '{emailAddress}' on {searchEmail_Field}")
    public MailiNator_Page_NewTab  sendKeysSearchEmail(String emailAddress){ // It is search email field and also create a new demo email Address
        sendKeys(searchEmail_Field,emailAddress);
        return this;
    }

    @Step("Click search button   ")
    public MailiNator_Page_NewTab  clickMailiNatorPageSearchButton(){
        click(searchEmail_Button);
        return this;
    }

    @Step("Enter first recieved email   ")
    public MailiNator_Page_NewTab  getFirstRecievedEmail(){ // Enter the email and get the password from the email in the next step
        click(firstEmail);
        return this;
    }

    @Step("Enter email that contains password  ")
    public MailiNator_Page_NewTab getEmailContainCode(){
        click(emailWithCode);;
        return this;
    }

    @Step("Get password text  ")
    public String getPasswordText(){
       WebElement emailBody_iframe=driver.findElement(emailBodyIframe);
       driver.switchTo().frame(emailBody_iframe);
       return getText(password);
    }

}
