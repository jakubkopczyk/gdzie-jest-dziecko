package step_definitions;

import core.Capabilities;
import cucumber.api.java8.En;
import page_object.EnterPhoneNumberPage;
import page_object.LoginPage;

public class LoginSteps implements En {
    EnterPhoneNumberPage enterPhoneNumberPage;
    LoginPage loginPage;
    public LoginSteps() {
        And("^I login using data from the properties$", () -> {
            enterPhoneNumberPage = new EnterPhoneNumberPage(Capabilities.driver);
            loginPage = enterPhoneNumberPage.enterAlreadyRegisteredPhoneNumber(
                    enterPhoneNumberPage.accountData.getProperty("phoneNumber"));
            loginPage.loginWithCorrectCredentials(enterPhoneNumberPage.accountData.getProperty("password"));
        });

    }
}
