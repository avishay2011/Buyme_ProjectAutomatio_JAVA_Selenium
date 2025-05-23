package page_Objects;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.xml.sax.SAXException;
import utils.DropDownValues_And_ElementsMap;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.Utilities.readFromThisFile;

public class Purchase_GiftCard_Step1_Page extends BasePage{
    public Purchase_GiftCard_Step1_Page(WebDriver driver) {
        super(driver);
    }

    //Elenents
    private By firstStep_Title=By.xpath("//div[text()='למי לשלוח']");
    private By giftReciever_Name=By.id("שם מקבל המתנה-friendName");
    private By whatAreWeCelebrating_Dropdown_Button=By.cssSelector(".b2c-dropdown__wrapper__icon");
    private By blessingFreeText_Field=By.id("greeting-b2c-textarea");
    private By addPicOrVideo_Button=By.id("add-pic-selection_button__input");
    private By continue_Button=By.cssSelector("button[type=\"submit\"]");
    private By celebrationsReasons_List=By.cssSelector("ul[role=\"listbox\"]>li");
    private By forMySelf_Button=By.id("לעצמי-forMyself");
    private By giftForMySelf_Message=By.cssSelector(".purchase-flow-step-1__for-myself>label");

    // =============== Actions =====================
    // =============================================

    @Step
    public Purchase_GiftCard_Step1_Page sendKey_GiftReciever_Name(String recieverName){
        sendKeys(giftReciever_Name,recieverName);
        return this;
    }

    @Step
    public Purchase_GiftCard_Step1_Page sendKey_Blessing(String blessing){
        sendKeys(blessingFreeText_Field,blessing);
        return this;
    }

    @Step
    public Purchase_GiftCard_Step1_Page sendKeys_addPicOrVideo(String picPath){
        sendKeys(addPicOrVideo_Button,picPath);
        return this;
    }

    @Step
    public Purchase_GiftCard_Step1_Page select_CelebrationReason(String celebrationReason){
        click(whatAreWeCelebrating_Dropdown_Button);
        List<WebElement> celebrationReasons_List=driver.findElements(celebrationsReasons_List);
        for (WebElement element:celebrationReasons_List)
            if(element.getText().contains(celebrationReason)){
                element.click();
                break;
            }
        return this;
    }
    @Step
    public Purchase_GiftCard_Step1_Page click_Continue(){
        click(continue_Button);
        return this;
    }
    @Step
    public Purchase_GiftCard_Step1_Page click_For_Myself(){
        click(forMySelf_Button);
        return this;

    }

    // =============== Getters and validations =====================
    // =============================================

    @Step
    public boolean isPurchasePageStep1Opened() throws ParserConfigurationException, IOException, SAXException { //The name price inside the url is indication that is step 1 in the purchase process
        return isElementDisplayed(firstStep_Title) && driver.getCurrentUrl().contains(readFromThisFile("purchasePageURL_Step1"));
    }


    @Step@Description("Verify that all expected elements in page displayed") /// The expected elements and their names have taken from another class
    public boolean areAllExpectedElementsDisplayed(){  ///Map include string that contains name and that contains location
        Map<String, By> elementsMap = DropDownValues_And_ElementsMap.getElementsMap(); //If element (found by location) not displayed return false , fail the method and report allure with the element name
        boolean areAllElementsDisplayed = true;
        for (Map.Entry<String, By> entry : elementsMap.entrySet()) {
            String elementName = entry.getKey();
            By locator = entry.getValue();
            try {
                if (!driver.findElement(locator).isDisplayed()) {
                    Allure.step("❌ Element not displayed: " + elementName, Status.FAILED);
                    areAllElementsDisplayed = false;
                    Assert.fail();
                }
            } catch (NoSuchElementException error) {
                Allure.step("❌ Element not found in DOM: " + elementName, Status.FAILED);
                areAllElementsDisplayed = false;
                Assert.fail();
            }
        }

        if (areAllElementsDisplayed) {
            Allure.step("✅ All expected elements are displayed");
        }

        return areAllElementsDisplayed;
    }

    @Step
    public String getTextgiftCardForMyselfMessage(){
        return getText(giftForMySelf_Message);
    }

}
