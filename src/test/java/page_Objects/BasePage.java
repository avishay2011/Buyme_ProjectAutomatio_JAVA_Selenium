package page_Objects;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static utils.Utilities.takeScreenShot;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait; //= new WebDriverWait(driver, Duration.ofSeconds(10));
    protected Actions action;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.action= new Actions(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    ///Action methods on elements : click, send keys and etc

    public void click(By elementLocation) {
        try {
            WebElement element=waitVisibility(elementLocation);
            element.click();
            Allure.step("Clicking on element " + elementLocation.toString());
        }
        catch (Exception error){
            Allure.step("Clicking failed: " + error.getMessage(), Status.FAILED);
            takeScreenShot(driver);
            throw new RuntimeException("Click failed on element: " + elementLocation, error);
        }
    }


    public void sendKeys(By elementLocation, String text) {
        try {
            WebElement element=waitVisibility(elementLocation);
            element.clear();
            element.sendKeys(text);
            Allure.step("Sending keys to the element " + elementLocation.toString());
        }
        catch (Exception error){
           Allure.step("Sending keys failed: " + error.getMessage(), Status.FAILED);
           takeScreenShot(driver);
           throw new RuntimeException("Send keys failed on element: " + elementLocation, error);
       }
    }



    public void hoverToElement(By elementLocation) { /// this function have written for fields that user need to hover on it before the click fuction is possible
        try {
            WebElement element=waitVisibility(elementLocation);
            action.moveToElement(element).build().perform();
            Allure.step("Hovering to element " + elementLocation.toString());
        }
        catch (Exception error){
            Allure.step("Hovering to element failed " + error.getMessage(), Status.FAILED);
            takeScreenShot(driver);
            throw new RuntimeException("Hover to element failed on element: " + elementLocation, error);
        }
    }

    /// Get  text,css values and attributes

    public String getText(By elementLocation) {
        try {
            WebElement element=waitVisibility(elementLocation);
            String text = element.getText();
            Allure.addAttachment("Text from element", text);
            return text;
        } catch (Exception error) {
            Allure.step("❌ Failed to get text from element: " + elementLocation + " → " + error.getMessage(), Status.FAILED);
            takeScreenShot(driver);
            return "Web element not found";
        }
    }


    public String getColor(By elementLocation) {
        try {
            WebElement element=waitVisibility(elementLocation);
            String color = element.getCssValue("color");  //return rgba that is the format that define color in css
            Allure.addAttachment("Element color is :",color);
            return color;
        } catch (Exception error) {
            Allure.step("❌ Failed to get color from element: " + elementLocation + "   →   " + error.getMessage(), Status.FAILED);
            takeScreenShot(driver);
            return "Web element not found";
        }
    }


    public String getInputFieldValue(By elementLocation){  //Get the value displayed in the input field after user input
        try {
            WebElement element=waitVisibility(elementLocation);
            String text = element.getAttribute("value");
            Allure.addAttachment("Text from element", text);
            return text;
        } catch (Exception error) {
            Allure.step("❌ Failed to get input field text from : " + elementLocation + " → " + error.getMessage(), Status.FAILED);
            takeScreenShot(driver);
            return "";
        }
    }

    ////Validations
    //Not verification . I use it just for checking verification methods in page objects
    @Step("Check that the requested page is loaded without any errors or mistakes") ///General method that can be used for any page for checking that one of the major error not appears
    public boolean pageLoadedWithNoErrors() {
        wait.until( webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        boolean noErrors=true;
        String pageSource = driver.getPageSource().toLowerCase();
        String[] criticalErrors = {
                "internal server error",
                "page not found",
                "an unexpected error occurred",
                "שגיאה בטעינת הדף"
        };

        for (String error : criticalErrors) {
            if(pageSource.contains(error)) {
                System.out.println("הודעת שגיאה התגלתה בדף: " + error);
                noErrors=false;
            }

        }
        return noErrors;
    }


    public boolean isElementDisplayed(By elementLocation){
        try {
            waitVisibility(elementLocation);
            Allure.step("Element is displayed " + elementLocation.toString());
            return true;
        }
        catch(Exception error){
            Allure.step("❌ Failed to get element visibility: " + elementLocation.toString() + " → " + error.getMessage(), Status.FAILED);
            takeScreenShot(driver);
            return false;
        }
    }

    @Step("Verify that error message is displayed *below* the input field") //להעביר ל BASE
    //////lower y value means that the element located actually higher on the screen
    public boolean isElement1LocatedBelowElement2(By upperElement,By lowerElement) {
        try {
            Point upperElementPoint = waitVisibility(upperElement).getLocation();
            Point lowerElementPoint = waitVisibility(lowerElement).getLocation();
            if (upperElementPoint.getY() < lowerElementPoint.getY()) {
                Allure.step("✔ Element " + lowerElement + " is correctly displayed below " + upperElement );
                return true;
            } else {
                Allure.step("❌ Element " + lowerElement + " is NOT displayed below " + upperElement, Status.FAILED );
                takeScreenShot(driver);
                return false;
            }
        } catch (Exception error) {
            Allure.step("❌ Exception during element position check: " + error.getMessage(), Status.FAILED );
            takeScreenShot(driver);
            return false;
        }
    }

    @Step
    public boolean areDropdownValuesMatchExpected(List<String> expectedValues,By actualValues) {
        List<WebElement> actualAmountsList = driver.findElements(actualValues);
        if (actualAmountsList.size() != expectedValues.size()) {
            Allure.step("Expected list size = " + expectedValues.size());
            Allure.step("Actual list size= " +actualAmountsList.size());
            return false;
        }
        String actualText;
        String expectedText;
        for (int i = 0; i < expectedValues.size(); i++) {
            actualText = actualAmountsList.get(i).getText().trim();
            expectedText = expectedValues.get(i).trim();
            if (!expectedText.equalsIgnoreCase(actualText)) {
                Allure.step("Mismatch at index : " +i);
                Allure.step("Expected : " +expectedText);
                Allure.step("Actual : "   +actualText );
                return false;
            }
        }
        return true;
    }


    /// Wait method
    public WebElement waitVisibility(By elementLocation) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException error) {
            error.printStackTrace();
        }
        return wait.until(ExpectedConditions.visibilityOf(driver.findElement(elementLocation)));
    }
}

