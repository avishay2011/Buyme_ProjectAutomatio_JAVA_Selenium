package test;


import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.asserts.SoftAssert;
import org.xml.sax.SAXException;
import page_Objects.*;


import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static utils.Utilities.readFromThisFile;


@Listeners({AllureTestNg.class})
public class BaseTest {

    protected static WebDriver driver;
    protected static Actions actions;
    protected static WebDriverWait wait;
    protected SoftAssert softAssert;
    protected HomePage homePage;
    protected Registration_Page registration_Page;
    protected MailiNator_Page_NewTab mailiNator_Page_NewTab;
    protected MyAccountDetails_Page myAccountDetails_page;
    protected SearchResults_Page searchResults_page;
    protected BirthDayGifts_Page birthDayGiftsPage;
    protected Coupon_Page couponPage;
    protected Purchase_GiftCard_Step1_Page purchaseGiftCard_Step1_Page;
    protected Purchase_GiftCard_Step2_Page purchaseGiftCard_Step2_Page;
    protected GiftCard_Balance_Page giftCard_balance_page;
    protected GiftsForEmployees_Page giftsForEmployees_page;


    @BeforeClass
    public  void setUp() throws ParserConfigurationException, IOException, SAXException {
        // DEFINE  DRIVER
        initBrowser(readFromThisFile("browser"));
        actions = new Actions(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void uploadSite() throws ParserConfigurationException, IOException, SAXException {
        softAssert = new SoftAssert(); ///Important Reinitialize SoftAssert in @BeforeMethod so that each test starts with clean softassertions data
        homePage = new HomePage(driver);
        registration_Page=new Registration_Page(driver);
        mailiNator_Page_NewTab=new MailiNator_Page_NewTab(driver);
        myAccountDetails_page=new MyAccountDetails_Page(driver);
        searchResults_page=new SearchResults_Page(driver);
        birthDayGiftsPage=new BirthDayGifts_Page(driver);
        couponPage=new Coupon_Page(driver);
        purchaseGiftCard_Step1_Page=new Purchase_GiftCard_Step1_Page(driver);
        purchaseGiftCard_Step2_Page=new Purchase_GiftCard_Step2_Page(driver);
        giftCard_balance_page=new GiftCard_Balance_Page(driver);
        giftsForEmployees_page=new GiftsForEmployees_Page(driver);
        driver.get(readFromThisFile("url"));
    }



    public void initBrowser(String BrowserName) {
        if (BrowserName.equalsIgnoreCase("chrome"))
            driver = initChromeDriver();
        else if (BrowserName.equalsIgnoreCase("edge"))
            driver = initEdgeDriver();
        else if (BrowserName.equalsIgnoreCase("firefox"))
            driver = initFireFoxDriver();
        else
            throw new RuntimeException("Invalid browser type- Please select Chrome ,firefox or  edge driver  only");
    }

    public WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        return driver;
    }

    public WebDriver initEdgeDriver() {
        driver = new EdgeDriver();
        return driver;
    }

    public WebDriver initFireFoxDriver() {
        driver = new FirefoxDriver();
        return driver;
    }

    public String getCurrentTabHandle() {
        return driver.getWindowHandle();
    }

    public void switchToTab(String handle) {
        driver.switchTo().window(handle);
    }


    public void closeCurrentTabAndSwitchTo(String handle) {
        driver.close();
        switchToTab(handle);
    }

    //  @AfterClass
    public static void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}
