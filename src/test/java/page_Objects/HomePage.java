
package page_Objects;



import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import utils.DropDownValues_And_ElementsMap;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

    //constructor
    public HomePage(WebDriver driver){
        super(driver);
    }

    //elements
    private By login_Button=By.cssSelector(".notSigned>a");
    private By searchButton=By.cssSelector("a[href=\"https://buyme.co.il/search\"]"); ///Unique element .also used for verify that the right page opened
    private By myAccount_DropDown=By.xpath("//span[text()='החשבון שלי']");
    private By myAccount_Details=By.xpath("//span[text()='פרטי חשבון']");
    private By amounts_Dropdown_Button=By.cssSelector("div>span[alt=\"סכום\"]"); ///This location is visible only while amount field is clear,so it's also good for check the clear button
    private By regions_Dropdown_Button=By.cssSelector("div>span[alt=\"אזור\"]");/// This location is visible only while region field is clear,so it's also good for check the clear button
    private By categories_Dropdown_Button=By.cssSelector("div>span[alt=\"קטגוריה\"]");/// This location is visible only while category field is clear,so it's also good for check the clear button
    private By dropDownList_Values=By.cssSelector(".show-options>select>option"); ///all dropdown are on the same location and it's works properly because just one dropdown can be opened at the same time
    private By findGift_Button=By.cssSelector(".search-bar-wrapper a");
    private By clearSearch_Button=By.xpath("//span[text()='נקה חיפוש']");
    private By logOut_Button=By.xpath("//span[text()='יציאה']");
    private By closePopUp_Button=By.cssSelector("button[aria-label=\"close\"]");
    private By birthDayGifts_Link=By.cssSelector("a[title=\"מתנות ליום הולדת\"]");
    private By checkGiftCardBalance_Button=By.xpath("//a[text()='בדיקת יתרה בGift Card']");
    private By giftsForEmployees_Button=By.partialLinkText("מתנות לעובדים");
    private By closePopUpButton2=By.cssSelector("button[aria-label=\"כפתור סגירה\"]");



    // =============== Actions =====================
    // =============================================
    @Step
    public HomePage navigateToRegistrationPage(){
        click(login_Button);
        return this;
    }



    @Step
    public boolean homePageIsDisplayed() {  // verify that the right page loaded by  elem location that unique for page. Search button is unique for home page
            return isElementDisplayed(searchButton);
    }

    @Step
    public HomePage navigateToMyAccountDetailsPage(){
         hoverToElement(myAccount_DropDown);
         click(myAccount_Details);
         return this;
        }

    @Step
    public HomePage openAmounts_Dropdown(){
        click(amounts_Dropdown_Button);
        return this;
    }

    @Step
    public HomePage openRegions_Dropdown(){
        click(regions_Dropdown_Button);
        return this;
    }

    @Step
    public HomePage openCategories_Dropdown(){
        click(categories_Dropdown_Button);
        return this;
    }

    @Step
    @Description("Select  amounts range  in amounts dropdown")
    public HomePage selectAmount(String amount){
        List<WebElement> dropDownList_AmountsValues_Elems=driver.findElements(dropDownList_Values);
        for (WebElement elem: dropDownList_AmountsValues_Elems){
            if(elem.getText().contains(amount)){
                elem.click();
                break;
            }
        }
        return this;
    }

    @Step
    @Description("Select  region   in regions dropdown")
    public HomePage selectRegion(String region){
        List<WebElement> dropDownList_AmountsValues_Elems=driver.findElements(dropDownList_Values);
        for (WebElement elem: dropDownList_AmountsValues_Elems){
            if(elem.getText().contains(region)){
                elem.click();
                break;
            }
        }
        return this;
    }

    @Step
    @Description("Select  category   in categories dropdown")
    public HomePage selectCategory(String category){
        List<WebElement> dropDownList_AmountsValues_Elems=driver.findElements(dropDownList_Values);
        for (WebElement elem: dropDownList_AmountsValues_Elems){
            if(elem.getText().contains(category)){
                elem.click();
                break;
            }
        }
        return this;
    }

    @Step
    public HomePage clickFindMeGift(){
        click(findGift_Button);
        return this;
    }

    @Step
    public HomePage click_BirthDayGifts_Link(){
        click(birthDayGifts_Link);
        return this;
    }

    @Step
    @Description("If pop up appears close it ")
    public HomePage clickClosePopup(){
        try {
            List<WebElement> popupsClose=driver.findElements(closePopUp_Button);
            if(!popupsClose.isEmpty() && popupsClose.get(0).isDisplayed()){
                popupsClose.get(0).click();
            }
        }
        catch (Exception error){
        }
        return this;
    }

    @Step
    @Description("If pop up appears close it ")
    public HomePage clickClosePopup2(){
        try {
            List<WebElement> popupsClose=driver.findElements(closePopUpButton2);
            if(!popupsClose.isEmpty() && popupsClose.get(0).isDisplayed()){
                popupsClose.get(0).click();
            }
        }
        catch (Exception error){
        }
        return this;
    }

    @Step
    public  HomePage navigateToGiftsForEmployees(){
        click(giftsForEmployees_Button);
        List<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        return this;
    }

    @Step
    public HomePage clearSearch(){
        click(clearSearch_Button);
        return this;
    }

    @Step
    public HomePage clickcheckGiftCardBalanceButton(){
        click(checkGiftCardBalance_Button);
        return this;
    }

    @Step
    public HomePage logOut(){
        try {
            hoverToElement(myAccount_DropDown);
            List<WebElement> closeButtons = driver.findElements(logOut_Button);
            if (!closeButtons.isEmpty() && closeButtons.get(0).isDisplayed()) {
                closeButtons.get(0).click();
            }
        }
        catch (Exception error){
        }
        return this;
    }


    // =============== Getters and validations =====================
    // =============================================


    @Step
    @Description("Verify that the amount range  inside the dropdown list are correct") ///The helper method for checking this boolean is in base page and the expected have taken from another class
    public boolean areAllAmountsDropdownValuesMatchExpected(){
        return areDropdownValuesMatchExpected(DropDownValues_And_ElementsMap.getExpectedAmounts(),dropDownList_Values);
    }

    @Description("Verify that all country Regiongs  inside the dropdown list are correct") ///The helper method for checking this boolean is in base page and the expected have taken from another class
    @Step
    public boolean areAllRegionsDropdownValuesMatchExpected(){
        return areDropdownValuesMatchExpected(DropDownValues_And_ElementsMap.getExpectedRegions(),dropDownList_Values);
    }

    @Description("Verify that all  Categories  inside the dropdown list are correct") ///The helper method for checking this boolean is in base page and the expected have taken from another class
    @Step
    public boolean areAllCategoriesDropdownValuesMatchExpected(){
        return areDropdownValuesMatchExpected(DropDownValues_And_ElementsMap.getExpectedCategories(),dropDownList_Values);
    }



    @Step
    public boolean isAmountFieldIsClear(){
        return isElementDisplayed(amounts_Dropdown_Button); ////This element is displayed only while amount field is clear
    }

    @Step
    public boolean isRegionFieldIsClear(){
        return isElementDisplayed(regions_Dropdown_Button); ////This element is displayed only while region field is clear
    }

    @Step
    public boolean isCategoryFieldIsClear(){
        return isElementDisplayed(categories_Dropdown_Button); ////This element is displayed only while category field is clear
    }


}






