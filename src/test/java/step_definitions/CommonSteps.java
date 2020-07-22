package step_definitions;

import core.Capabilities;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java8.En;
import framework.helpers.Adb;
import framework.helpers.ObjectByName;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.InvalidArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import page_object.WelcomePage;

import static core.Capabilities.appiumSession;
import static framework.helpers.ObjectByName.pages;

public class CommonSteps implements En {
    public static AppiumDriver driver = Capabilities.driver;
    private static final Logger logger = LoggerFactory.getLogger(CommonSteps.class.getSimpleName());
    private final int SCREEN_RECORDING_TIMEOUT = 120;
    private final long START_TIME = System.currentTimeMillis();
    private int part = 1;

    WelcomePage welcomePage;

    public CommonSteps() {
        When("^I launch the application$", () -> {
            welcomePage = new WelcomePage(Capabilities.driver);
        });

        When("^I tap on the \"([^\"]*)\" button on the \"([^\"]*)\" page$", (String buttonName, String pageName) -> {
            buttonName = buttonName.toLowerCase();
            buttonName = StringUtils.capitalize(buttonName);
            buttonName = buttonName.replaceAll("\\s+", "");
            pageName = pageName.replaceAll("\\s+", "").toLowerCase();
            ObjectByName.invokeClassMethod(pages.get(pageName), "click" + buttonName + "Button");
        });
        When("^I swipe \"([^\"]*)\" \"([^\"]*)\" times$", (String direction, String howManyTimes) -> {
            switch (direction.toLowerCase()) {
                case "left":
                    welcomePage.swipeLeft(Integer.parseInt(howManyTimes));
                    break;
                case "right":
                    welcomePage.swipeRight(Integer.parseInt(howManyTimes));
                default:
                    throw new InvalidArgumentException(direction + " is not a correct direction.");
            }
        });
        Then("^\"([^\"]*)\" page is displayed$", (String pageName) -> {
            pageName = pageName.replaceAll("\\s+", "").toLowerCase();
            ObjectByName.invokeClassMethod(pages.get(pageName), "is" + StringUtils.capitalize(pageName) + "PageDisplayed");
        });
    }

    @Before
    public void setUpScenario(Scenario scenario) {
        System.out.println();
        logger.info(String.format("[SCENARIO] %s", scenario.getName()));
        System.out.println();
        Adb.startRecordingScenario("medium", scenario.getName(), appiumSession.getProperty("udid"), part);
        if ((System.currentTimeMillis() - START_TIME) / 1000.00 > SCREEN_RECORDING_TIMEOUT) {
            Adb.stopCurrentRecording(appiumSession.getProperty("udid"));
            part += 1;
            Adb.startRecordingScenario("medium", scenario.getName(), appiumSession.getProperty("udid"), part);
        }
        driver.closeApp();
        driver.launchApp();
    }

    @After
    public void afterScenario(Scenario scenario) {
        Adb.stopCurrentRecording(appiumSession.getProperty("udid"));
        Adb.pullScenarioVideo(scenario.getName(), appiumSession.getProperty("udid"));
    }
}
