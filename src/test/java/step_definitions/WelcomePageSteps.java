package step_definitions;

import core.Capabilities;
import cucumber.api.java8.En;
import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import page_object.WelcomePage;

public class WelcomePageSteps implements En {
    WelcomePage welcomePage;
    public WelcomePageSteps() {
        Then("^I can see \"([^\"]*)\" description$", (String description) -> {
            welcomePage = new WelcomePage(Capabilities.driver);
            Assert.assertEquals(welcomePage.returnDescriptionText().replace("\n", " ")
                    .trim().replaceAll(" +", " "), description);
        });
    }
}
