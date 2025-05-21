package page_Objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


import java.util.List;

public class BirthDayGifts_Page extends BasePage{
    public BirthDayGifts_Page(WebDriver driver) {
        super(driver);
    }

    //elements
    private By birthDay_gifts_List=By.cssSelector(".bm-product-card-link span");

    private List<WebElement> getBirthDayGiftsList() {
        return getElementsFromListLocation(birthDay_gifts_List);
    }

    @Step
    public boolean isBirthDayGiftsDisplayed(){
        List<WebElement> birthDayGiftsList=getBirthDayGiftsList();
        return !birthDayGiftsList.isEmpty() && birthDayGiftsList.stream().allMatch(WebElement::isDisplayed) ;
    }
    @Step
    public BirthDayGifts_Page selectGift(String giftForSearch){
        List<WebElement> birthDayGiftsList=getBirthDayGiftsList();
        for (WebElement element:birthDayGiftsList){
            if(element.getText().toLowerCase().contains(giftForSearch)){
               element.click();
               break;
            }
        }
        return this;
    }
}
