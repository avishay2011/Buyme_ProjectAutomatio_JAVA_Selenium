package page_Objects;

import org.openqa.selenium.WebDriver;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.Utilities.readFromThisFile;

public class Purchase_GiftCard_Step2_Page extends BasePage{
    public Purchase_GiftCard_Step2_Page(WebDriver driver) {
        super(driver);
    }

    //Functions

    public boolean isPurchasePageStep2HowToSendOpened() throws ParserConfigurationException, IOException, SAXException { //The name price inside the url is indication that is step 1 in the purchase process
        return driver.getCurrentUrl().contains(readFromThisFile("purchasePageURL_Step1")) && driver.getCurrentUrl().contains(readFromThisFile("purchasePageURL_Step2"));
    }

}
