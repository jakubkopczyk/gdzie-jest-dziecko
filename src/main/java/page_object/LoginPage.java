package page_object;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BasePage {
    @AndroidFindBy(id = "pl.locon.gjd:id/activity_login_password")
    private MobileElement passwordEdit;

    @AndroidFindBy(id = "pl.locon.gjd:id/activity_login_button")
    private MobileElement loginBtn;

    public HomePage loginWithCorrectCredentials(String password){
        waitForElement(passwordEdit);
        passwordEdit.sendKeys(password);
        waitForElement(loginBtn);
        loginBtn.click();
        return new HomePage(driver);
    }

    public LoginPage(AppiumDriver driver) {
        super(driver);
    }
}
