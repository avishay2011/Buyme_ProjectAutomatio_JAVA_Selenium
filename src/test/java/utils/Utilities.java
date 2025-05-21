package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class Utilities {
    static final String configPath = "src/testData/config.xml";

    public static String takeScreenShot(WebDriver driver) {
        try {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String fileName = timeStamp + "_" + System.currentTimeMillis() + ".png";
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File screenShotFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            Path destinationPath = new File("test-output/images/" + fileName).toPath();
            Files.createDirectories(destinationPath.getParent());
            Files.copy(screenShotFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // Add to allure report
            Allure.addAttachment("Screenshot - " + timeStamp,
                    Files.newInputStream(screenShotFile.toPath()));

            return "images/" + fileName;
        } catch (IOException e) {
            System.out.println("Failed to save screenshot: " + e.getMessage());
            return null;
        }
    }

    public static String generateRandomString() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    private static String readFromFile(String keyData, String pathName) throws ParserConfigurationException, IOException, SAXException {
        String value = null;
        try {
            // Load the XML file
            File xmlFile = new File(pathName.toString());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // Normalize the document
            doc.getDocumentElement().normalize();

            // Extract values from XML
            value = doc.getElementsByTagName(keyData).item(0).getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return value;
    }

    public static String readFromThisFile(String keyData) throws ParserConfigurationException, IOException, SAXException {
        return readFromFile(keyData, configPath);
    }
}
