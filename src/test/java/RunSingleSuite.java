import core.Capabilities;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import framework.helpers.Adb;
import framework.helpers.AppiumStartServer;
import io.appium.java_client.AppiumDriver;
import cucumber.api.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import static core.Capabilities.appiumSession;

@CucumberOptions(plugin = {
        "json:target/cucumber-report/cucumber-json.json", "html:target/cucumber-report/cucumber-html/",
        "pretty:target/cucumber-report/cucumber-pretty.txt", "junit:target/cucumber-report/cucumber-junit.xml", "usage:target/cucumber-report/cucumber-usage.json"},
        features = "src/test/features",
        tags = {"@Login"})


public class RunSingleSuite extends AbstractTestNGCucumberTests {
    public static AppiumDriver driver = Capabilities.driver;
    static AppiumStartServer appiumServer = new AppiumStartServer();

    @BeforeSuite
    public static void setUp() throws Exception {
        appiumServer.startServer();
        Capabilities.setUp();
        Adb.removePreviousVideos(appiumSession.getProperty("udid"));
        driver = Capabilities.driver;
    }

    @AfterSuite
    public static void tearDown() {
        driver.resetApp();
        driver.quit();
        appiumServer.stopServer();
    }
}

