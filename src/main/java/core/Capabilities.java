package core;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class Capabilities {
    public static AppiumDriver driver;
    public static WebDriverWait wait;
    public static Properties appiumSession = new Properties();


    public static void setUp() throws Exception
    {
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/appiumSession.properties");
            appiumSession.load(input);
            DesiredCapabilities capabilities = DesiredCapabilities.android();
            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, appiumSession.getProperty("deviceName"));
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, appiumSession.getProperty("platformVersion"));
            capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, appiumSession.getProperty("platformName"));
            capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
            capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, appiumSession.getProperty("timeout"));
            capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, appiumSession.getProperty("automationName"));
            capabilities.setCapability("deviceOrientation", appiumSession.getProperty("deviceOrientation"));
            capabilities.setCapability("autoLaunch", appiumSession.getProperty("autoLaunch"));
            capabilities.setCapability("appPackage", appiumSession.getProperty("appPackage"));
            capabilities.setCapability("appActivity", appiumSession.getProperty("appActivity"));
            capabilities.setCapability("newCommandTimeout", appiumSession.getProperty("timeout"));
            capabilities.setCapability("autoGrantPermissions", appiumSession.getProperty("autoGrantPermissions"));
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            driver.launchApp();
            wait = new WebDriverWait(driver, 60);
        }catch(IOException io){
            io.printStackTrace();
        }

    }
    public void AfterSetUp() {
        driver.closeApp();
        driver.launchApp();
    }
}
