package flows;

import org.xml.sax.SAXException;
import page_Objects.HomePage;
import page_Objects.SearchResults_Page;
import test.BaseTest;
import verifications.Verifications;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static utils.Utilities.readFromThisFile;

public class FilterAndSearchGiftsFlow extends BaseTest {
    private HomePage homePage=new HomePage(getDriver());
    private SearchResults_Page searchResults_page=new SearchResults_Page(getDriver());

    public FilterAndSearchGiftsFlow dropdownSelectionFlow() throws ParserConfigurationException, IOException, SAXException {
        homePage.clickClosePopup()
                .openAmounts_Dropdown();
        Verifications.verifyTrue(homePage.areAllAmountsDropdownValuesMatchExpected(), "Verify that all the amounts values on dropdown are as expected");
        homePage.selectAmount(readFromThisFile("amount"))
                .openRegions_Dropdown();
        Verifications.verifyTrue(homePage.areAllRegionsDropdownValuesMatchExpected(), "Verify that all the regions values on dropdown are as expected ");
        homePage.selectRegion(readFromThisFile("region"))
                .openCategories_Dropdown();
        Verifications.verifyTrue(homePage.areAllCategoriesDropdownValuesMatchExpected(), "Verify that all the category values on dropdown are as expected ");
        homePage.selectCategory(readFromThisFile("category"))
                .clickFindMeGift()
                .clickClosePopup2();
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("amount")), "Has the amount value for search included in results"); ///Check that the correct amount appears on results
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("region")), "Has the region value for search included in results");///Check that the correct region appears on results
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("category")), "Has the region category value for search included in results");///Check that the correct category appears on results//The first test only verifies that all conditions from the @BeforeMethod are met."
        Verifications.assertAll();
        return this;
    }



}
