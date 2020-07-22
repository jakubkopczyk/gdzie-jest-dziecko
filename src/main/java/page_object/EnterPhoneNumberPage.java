package page_object;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class EnterPhoneNumberPage extends BasePage {
    @AndroidFindBy(id = "pl.locon.gjd:id/activity_enter_number_header_text_view")
    private MobileElement header;

    @AndroidFindBy(id = "pl.locon.gjd:id/activity_enter_number_msisdn_edit_text")
    private MobileElement phoneNumberEdit;

    @AndroidFindBy(id = "pl.locon.gjd:id/activity_enter_number_rodo_text_view")
    private MobileElement rodoText;

    @AndroidFindBy(id = "pl.locon.gjd:id/activity_enter_number_next_button")
    private MobileElement nextBtn;

    @AndroidFindBy(id = "android:id/message")
    private MobileElement confirmationText;

    @AndroidFindBy(id = "android:id/button1")
    private MobileElement yesBtn;

    public EnterPhoneNumberPage(AppiumDriver driver) {
        super(driver);
    }

    public LoginPage enterAlreadyRegisteredPhoneNumber(String phoneNumber) {
        waitForElement(phoneNumberEdit);
        phoneNumberEdit.sendKeys(phoneNumber);
        waitForElement(nextBtn);
        nextBtn.click();
        return new LoginPage(driver);
    }
}

