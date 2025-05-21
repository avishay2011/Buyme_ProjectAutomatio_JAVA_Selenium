package page_Objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BirthDayGifts_Page extends BasePage{
    public BirthDayGifts_Page(WebDriver driver) {
        super(driver);
    }

    //elements
    private By birthDay_gifts_List=By.cssSelector(".bm-product-card-link span");
    private By birthDayGifts_Page_Title=By.xpath("//h1[text()='מתנות ליום הולדת']");

    @Step
    public boolean isBirthDayGifts_Displayed(){
        waitVisibility(birthDayGifts_Page_Title);
        List<WebElement> birthDayGiftsList=driver.findElements(birthDay_gifts_List);
        return !birthDayGiftsList.isEmpty() && birthDayGiftsList.stream().allMatch(WebElement::isDisplayed) ;
    }
    @Step
    public BirthDayGifts_Page selectGift(String giftForSearch){
        List<WebElement> birthDayGiftsList=driver.findElements(birthDay_gifts_List);
        for (WebElement element:birthDayGiftsList){
            if(element.getText().toLowerCase().contains(giftForSearch)){
               element.click();
               break;
            }
        }
        return this;
    }
}
