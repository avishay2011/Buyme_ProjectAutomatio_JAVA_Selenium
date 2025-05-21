package page_Objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class MyAccountDetails_Page extends BasePage{
    public MyAccountDetails_Page(WebDriver driver) {
        super(driver);
    }

    //elements
    private By firstName_Field=By.cssSelector("input[title=\"שם פרטי\"]");
    private By lastName_Field=By.cssSelector("input[title=\"שם משפחה\"]");
    private By cellPhone_Field=By.cssSelector("input[title=\"נייד\"]");

    //functions


    @Step
    public String getFirstNameInputValue(){
        return getInputFieldValue(firstName_Field);
    }

    @Step
    public String getLastNameInputValue(){
        return getInputFieldValue(lastName_Field);
    }

    @Step
    public String getCellPhoneInputValue(){
        return getInputFieldValue(cellPhone_Field);
    }


}
