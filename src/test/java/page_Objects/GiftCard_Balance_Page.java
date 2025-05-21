package page_Objects;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;


public class GiftCard_Balance_Page extends BasePage{
    public GiftCard_Balance_Page(WebDriver driver) {
        super(driver);
    }

    //elements

    private By giftCard_Code_Field=By.cssSelector(".box input[role=\"combobox\"]");
    private By datePicker_Button=By.id("calendar");
    private By daysList_DatePicker=By.cssSelector(".month-days>span");
    private By nextYear_Button=By.cssSelector(".year-arrow .icon.chevron_next_rtl");
    private By nextMonth_Button=By.cssSelector(".month-arrow .icon.chevron_next_rtl");
    private By selectedYearName=By.cssSelector(".selected-year-name");
    private By selectedMonthName=By.cssSelector(".selected-month-name");
    private By checkBalance_Button=By.cssSelector("button[gtm=\"לבדיקת היתרה\"]"); ///used also for verify expected screen opened
    private By errorMessage_WrongGiftCardCode=By.cssSelector(".error-message.center>span");
    private By whatIsCardExpirationDate_Text=By.xpath("//span[text()='מה תוקף השובר?']");
    private By whatIsGiftCarddCode_Text=By.cssSelector(".bm-input>div>span");
    private By used_GiftCard_Message=By.cssSelector(".center>h3");
    private By valid_giftCard_Balance=By.xpath("//h3[contains(., 'יתרת שובר מעודכנת')]");


     // =============== Actions =====================
     // =============================================

    @Step
    public GiftCard_Balance_Page sendKeys_CouponField(String couponCode)  {
        sendKeys(giftCard_Code_Field,couponCode);
        return this;
    }

    @Step
    public GiftCard_Balance_Page click_DatePicker_Button(){
        click(datePicker_Button);
        return this;
    }

    @Step
    public GiftCard_Balance_Page click_CheckBalance_Button(){
        click(checkBalance_Button);
        return this;
    }

    @Step
    public GiftCard_Balance_Page select_Date_Picker(String day,String month,String year) throws InterruptedException {
        select_Month_DatePicker(month);
        select_Year_DatePicker(year);
        select_Day_DatePicker(day);
        return this;
    }


    @Step
    public GiftCard_Balance_Page select_Day_DatePicker(String day){
        List<WebElement> daysList=driver.findElements(daysList_DatePicker);
        boolean found = false;
        for (WebElement element:daysList){
            if (element.getText().equalsIgnoreCase(day)){
                element.click();
                found = true;
                break;
            }
        }
        if (!found) {
            Allure.step("Day '" + day + "' was not found in the calendar, skipping click", Status.FAILED);
        }
        return this;
    }

    @Step
    public GiftCard_Balance_Page select_Month_DatePicker(String month) throws InterruptedException {
        int clicksCount  =0;
        while (!getText(selectedMonthName).equals(month) && clicksCount <12){
            click(nextMonth_Button);
            clicksCount ++;
        }
        if (!getText(selectedMonthName).equals(month)) {
            Allure.step("Failed to select month '" + month + "' after " + clicksCount + " clicks", Status.FAILED);
        }
        return this;
    }

    @Step
    public GiftCard_Balance_Page select_Year_DatePicker(String year) throws InterruptedException {
        int clicksCount  =0;
        while (!getText(selectedYearName).equals(year) && clicksCount <12 ) {
            click(nextYear_Button);
            clicksCount ++;
        }
        if (!getText(selectedYearName).equals(year)) {
            Allure.step("Failed to select year '" + year + "' after " + clicksCount + " clicks", Status.FAILED);
        }
        return this;
    }

     // =============== Getters and validations =====================
     // =============================================

    @Step
    public boolean isGiftBalanceScreenOpen(){
        return  isElementDisplayed(checkBalance_Button);
    }

    @Step
    public String getText_WhatIsExpirationDate_MessageText(){
        return getText(whatIsCardExpirationDate_Text);
    }


    @Step
    public String getText_WhatIsYourCodeMessageText(){
        return getText(whatIsGiftCarddCode_Text);
    }

    @Step
    public String getText_WrongGiftCardCode_ErrorMessage(){
        return getText(errorMessage_WrongGiftCardCode);
    }

    @Step
    public String getColor_WrongGiftCardCode_ErrorMessage() {
        return getColor(errorMessage_WrongGiftCardCode);
    }

    @Step
    public String getText_Valid_GiftCardBalance_Message(){
        return getText(valid_giftCard_Balance);
    }
    @Step
    public String getText_UsedGiftCard_BalanceMessage(){
        return getText(used_GiftCard_Message);
    }


}
