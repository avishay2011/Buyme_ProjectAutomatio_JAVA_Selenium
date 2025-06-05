package flows;

import org.xml.sax.SAXException;
import page_Objects.GiftsForEmployees_Page;
import page_Objects.HomePage;
import test.BaseTest;
import verifications.Verifications;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.Utilities.readFromThisFile;

public class GiftsForEmployeesFlow extends BaseTest {
    private HomePage homePage=new HomePage(getDriver());
    private GiftsForEmployees_Page giftsForEmployees_page=new GiftsForEmployees_Page(getDriver());

    public GiftsForEmployeesFlow openBuyMeForBusinessFlow() throws ParserConfigurationException, IOException, SAXException {
        homePage.navigateToGiftsForEmployees();
        giftsForEmployees_page.clickJoinNowButton();
        Verifications.verifyTrue(giftsForEmployees_page.isEmployeesAmountFieldISDisplayed(),"Verify Elmployees amount field visibilty");
        Verifications.verifyTrue(getDriver().getCurrentUrl().contains(readFromThisFile("giftsForEmployeesPageUrl")),"Verify Gifts for employees page opens");
        Verifications.assertAll();
        return this;
    }

}
