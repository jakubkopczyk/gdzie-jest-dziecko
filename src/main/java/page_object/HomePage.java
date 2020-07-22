package page_object;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class HomePage extends BasePage {
    @AndroidFindBy(id = "pl.locon.gjd:id/activity_add_to_service_gps_card_view")
    private MobileElement addGpsCard;

    public HomePage(AppiumDriver driver) {
        super(driver);
    }

    public boolean isHomePageDisplayed(){
        return isElementPresent(addGpsCard);
    }
}
