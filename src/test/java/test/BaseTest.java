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

    // protected static WebDriver driver;
    protected static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
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
        initBrowser(readFromThisFile("browser")); ///Initilize driver
        actions = new Actions(getDriver());
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        getDriver().manage().window().maximize();
    }

    @BeforeMethod
    public void uploadSite() throws ParserConfigurationException, IOException, SAXException {
        softAssert = new SoftAssert(); ///Important Reinitialize SoftAssert in @BeforeMethod so that each test starts with clean softassertions data
        homePage = new HomePage(getDriver());
        registration_Page=new Registration_Page(getDriver());
        mailiNator_Page_NewTab=new MailiNator_Page_NewTab(getDriver());
        myAccountDetails_page=new MyAccountDetails_Page(getDriver());
        searchResults_page=new SearchResults_Page(getDriver());
        birthDayGiftsPage=new BirthDayGifts_Page(getDriver());
        couponPage=new Coupon_Page(getDriver());
        purchaseGiftCard_Step1_Page=new Purchase_GiftCard_Step1_Page(getDriver());
        purchaseGiftCard_Step2_Page=new Purchase_GiftCard_Step2_Page(getDriver());
        giftCard_balance_page=new GiftCard_Balance_Page(getDriver());
        giftsForEmployees_page=new GiftsForEmployees_Page(getDriver());
        getDriver().get(readFromThisFile("url"));
    }

    public static WebDriver getDriver() {  ///This method is good if I want to run the tests by parallel with more than one driver opens on the same time
        return driverThreadLocal.get();   /// Return the driver of the current run instead return simply driver. good if I want to run the tests parallel
    }

    public static void setDriver(WebDriver driver) { ///initializeddrive ThreadLocal and set by driver
        driverThreadLocal.set(driver);
    }



    public void initBrowser(String browserName) {  ///Select driver and initialize it .than initialize driverThreadLocal with the driver that selected
        WebDriver driver;
        if (browserName.equalsIgnoreCase("chrome"))
            driver = initChromeDriver();
        else if (browserName.equalsIgnoreCase("edge"))
            driver = initEdgeDriver();
        else if (browserName.equalsIgnoreCase("firefox"))
            driver = initFireFoxDriver();
        else
            throw new RuntimeException("Invalid browser type");
        setDriver(driver);  ///The driverThreadLocal is initialized with the driver that selelcted
    }

    public WebDriver initChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-popup-blocking"); // Block popups
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        return new ChromeDriver(options);
    }

    public WebDriver initEdgeDriver() {
        return  new EdgeDriver();
    }

    public WebDriver initFireFoxDriver() {
        return  new FirefoxDriver();
    }

    public String getCurrentTabHandle() {
        return getDriver().getWindowHandle();
    }

    public void switchToTab(String handle) {
        getDriver().switchTo().window(handle);
    }

    public void closeCurrentTabAndSwitchTo(String handle) {
        getDriver().close();
        switchToTab(handle);
    }


    @AfterClass
    public  void quit() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
        }
    }

}
