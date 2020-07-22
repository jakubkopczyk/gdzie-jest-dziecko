package page_object;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class WelcomePage extends BasePage {
    @AndroidFindBy(id = "pl.locon.gjd:id/activity_start_next_button")
    private MobileElement nextBtn;
    @AndroidFindBy(id = "pl.locon.gjd:id/activity_start_skip_button")
    private MobileElement skipBtn;
    @AndroidFindBy(id = "pl.locon.gjd:id/fragment_start_description_text_view")
    private MobileElement description;

    public WelcomePage(AppiumDriver driver) {
        super(driver);
    }

    public String returnDescriptionText() {
        waitForElement(description);
        return description.getText();
    }

    public void clickNextButton() {
        waitForElement(nextBtn);
        nextBtn.click();
    }

    public EnterPhoneNumberPage clickSkipButton() {
        waitForElement(skipBtn);
        skipBtn.click();
        return new EnterPhoneNumberPage(driver);
    }
}
