package verifications;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.qameta.allure.model.Status;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;
import page_Objects.BasePage;
import test.BaseTest;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.Utilities.readFromThisFile;
import static utils.Utilities.takeScreenShot;

public class Verifications extends BaseTest {

    private static SoftAssert softAssert = new SoftAssert();

    @Step("Verify text equals : {elementName}")
    public static void verifyTextEquals(String actual, String expected,String elementName) {  ///Element name clarify in allure report what field or message have checked
        Allure.step("Expected :" +expected);                                         /// The report contain expected value
        Allure.step("Actual :"   +actual);                                             /// The report contain actual value
        if(actual.equalsIgnoreCase("Web element not found")){
            Allure.step("❌ Cannot verify text : element not found ",Status.FAILED);  ///Information for allure report
            takeScreenShot(driver);                                                         ///Add screenshot
            softAssert.fail("Element not found – cannot verify text ");                     ///Collected to the steps that faild
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));        /// Sign the step as failed manually for report
            return;  // If element not found stop the test
        }
            softAssert.assertEquals(actual.trim(), expected.trim(), "Text mismatch"); /// If have text to compare with the expected text
        if (!actual.trim().equals(expected.trim())) {
            Allure.step("❌ Mismatch detected" , Status.FAILED);
            Allure.step("Actual " + actual);
            Allure.step("Actual " + expected);
            takeScreenShot(driver);
            softAssert.fail("Values did not match");
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
        }
        else {
            Allure.step("✅ ");
            takeScreenShot(driver);
        }
    }

    @Step("Verify color equals : {elementName}")
    public static void verifyTextColorIsRed(String actualColor,String elementName) throws ParserConfigurationException, IOException, SAXException {
        String expectedColor=readFromThisFile("rgba_Value_Of_Red");
        Allure.step("Expected :" +expectedColor);                                         /// The report contain expected value
        Allure.step("Actual :"   +actualColor);                                             /// The report contain actual value
        if(actualColor.equalsIgnoreCase("Web element not found")){
            Allure.step("❌ Cannot verify color : element not found ",Status.FAILED);  ///Information for allure report
            takeScreenShot(driver);                                                         ///Add screenshot
            softAssert.fail("Element not found – cannot verify color ");                     ///Collected to the steps that faild
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));        /// Sign the step as failed manually for report
            return;  // If element not found stop the test
        }
        softAssert.assertEquals(actualColor.trim(), expectedColor.trim(), "Colors mismatch"); /// If have text to compare with the expected text
        if (!actualColor.trim().equals(expectedColor.trim())) {
            Allure.step("❌ Colors mismatch detected", Status.FAILED);
            takeScreenShot(driver);
            softAssert.fail("Color rgb values did not match");
            Allure.getLifecycle().updateStep(step -> step.setStatus(Status.FAILED));
        }
        else {
            Allure.step("✅ ");
            takeScreenShot(driver);
        }
    }

    @Step("Verifying condition: {description}")
    public static void verifyTrue(boolean condition,String description) {
        Allure.step("Expected : true");
        Allure.step("Actual   : " + condition);
        if (!condition) {
            Allure.step("❌  "+description + "-Condition failed", Status.FAILED);
            takeScreenShot(driver);
            softAssert.fail("❌  " +description + "-Condition failed");
            Allure.getLifecycle().updateStep(s -> s.setStatus(Status.FAILED));
        } else {
            Allure.step("✅ Condition met "+description);
            takeScreenShot(driver);
        }
    }

    @Step("Assert all soft assertions")
    public static void assertAll() {
        softAssert.assertAll();
        takeScreenShot(driver);
    }
}