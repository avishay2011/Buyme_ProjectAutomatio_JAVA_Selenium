package page_Objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class GiftsForEmployees_Page extends BasePage {
    public GiftsForEmployees_Page(WebDriver driver) {
        super(driver);
    }

    //Elements
    private By joinNowButton=By.xpath("//button[text()='הצטרפו עכשיו!']");
    private By employeesAmountField=By.cssSelector("input[name=\"employeesAmount\"]");

    //methods
    @Step("Click - \"join now\" - button")
    public GiftsForEmployees_Page clickJoinNowButton(){
        click(joinNowButton);
        return this;
    }

    @Step
    public boolean isEmployeesAmountFieldISDisplayed(){
        return isElementDisplayed(employeesAmountField);
    }




}
