package page_Objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchResults_Page extends BasePage{
    public SearchResults_Page(WebDriver driver) {
        super(driver);
    }

    //elements
    public By searchResults_HeadLine=By.cssSelector(".application-main h1");


    //functions


    @Step
    public String getTextSearchResults(){
        return getText(searchResults_HeadLine);
    }


}
