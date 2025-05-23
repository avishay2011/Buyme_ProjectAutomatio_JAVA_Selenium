package test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import verifications.Verifications;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import static utils.Utilities.readFromThisFile;

@Listeners(utils.Listeners.class)
public class Tests02_FilterAndSearchGifts extends BaseTest {

    @BeforeMethod
    public void checkPageReadyAndPreConditions() throws ParserConfigurationException, IOException, SAXException {
        Assert.assertTrue(homePage.pageLoadedWithNoErrors());
        Assert.assertTrue(homePage.homePageIsDisplayed());
        homePage.openAmounts_Dropdown();
        Verifications.verifyTrue(homePage.isDropdownListIsVisible(),"Verify amounts dropdown list appears"); //check the last dropdown that opened -check amounts dropdown
        Verifications.verifyTrue(homePage.areAllAmountsDropdownValuesMatchExpected(),"Verify that all the amounts values on dropdown are as expected");
        homePage.selectAmount(readFromThisFile("amount"));
        homePage.openRegions_Dropdown();
        Verifications.verifyTrue(homePage.isDropdownListIsVisible(),"Verify regions dropdown list appears");//-check region dropdown
        Verifications.verifyTrue(homePage.areAllRegionsDropdownValuesMatchExpected(),"Verify that all the regions values on dropdown are as expected ");
        homePage.selectRegion(readFromThisFile("region"));
        homePage.openCategories_Dropdown();
        Verifications.verifyTrue(homePage.isDropdownListIsVisible(),"Verify categories dropdown list appears");//check the last dropdown that opened -check categories dropdown
        Verifications.verifyTrue(homePage.areAllCategoriesDropdownValuesMatchExpected(),"Verify that all the category values on dropdown are as expected ");
        homePage.selectCategory(readFromThisFile("category"));
        homePage.clickFindMeGift();
        homePage.clickClosePopup2();
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Filter potential gifts and verify that only the relevant gifts appears on list after filter ") //The same filtering process is performed at the beginning of both tests.
    public void test01_Filter_And_Search_Gifts() throws ParserConfigurationException, IOException, SAXException {
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("amount")),  "Has the amount value for search included in results"); ///Check that the correct amount appears on results
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("region")),  "Has the region value for search included in results");///Check that the correct region appears on results
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("category")),"Has the region category value for search included in results");///Check that the correct category appears on results//The first test only verifies that all conditions from the @BeforeMethod are met."
        Verifications.assertAll();
    }


    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Clear filter fields and verify that all the filter selections have made resets to default")
    public void test02_Filter_And_Clear_Filter_Fields() throws ParserConfigurationException, IOException, SAXException {
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("amount")),  "Has the amount value for search included in results"); ///Check that the correct amount appears on results
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("region")),  "Has the region value for search included in results");///Check that the correct region appears on results
        Verifications.verifyTrue(searchResults_page.getTextSearchResults().contains(readFromThisFile("category")),"Has the region category value for search included in results");///Check that the correct category appears on results
        driver.navigate().back();
        homePage.clearSearch();
        Verifications.verifyTrue(homePage.isAmountFieldIsClear(),  "Amounts dropdown field  has clear");
        Verifications.verifyTrue(homePage.isRegionFieldIsClear(),  "Regions dropdown field  has clear");
        Verifications.verifyTrue(homePage.isCategoryFieldIsClear(),"Categories dropdown field has clear");
        Verifications.assertAll();
    }
}
