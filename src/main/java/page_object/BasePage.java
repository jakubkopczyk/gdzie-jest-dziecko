package page_object;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.time.Duration.ofSeconds;

public abstract class BasePage {
    protected final AppiumDriver driver;
    public Properties accountData = new Properties();
    InputStream input = null;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);

        try {
            input = new FileInputStream("src/main/resources/accountData.properties");
            accountData.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MobileElement findElementWithTimeout(By by, int timeOutInSeconds) {
        return (MobileElement) (new WebDriverWait(driver, timeOutInSeconds)).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void waitForElement(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 25);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }
    }

    public boolean isElementPresent(MobileElement element){
        try{
            waitForElement(element);
            return true;
        } catch (NoSuchElementException e){
            return false;
        }
    }

    public void waitForElementToBeClickable(MobileElement element) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void back() {
        driver.navigate().back();
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private void scroll(int startx, int starty, int endx, int endy) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.longPress(PointOption.point(startx, starty))
                .waitAction(WaitOptions.waitOptions(ofSeconds(1)))
                .moveTo(PointOption.point(endx, endy))
                .release()
                .perform();
    }

    public void swipeLeft(int howMany) {
        Dimension size = driver.manage().window().getSize();
        int endx = (int) (size.width * 0.05);
        int startx = (int) (size.width * 0.95);
        int starty = size.height / 2;

        for (int i = 0; i <= howMany; i++) {
            scroll(startx, starty, endx, starty);
        }
    }

    public void swipeRight(int howMany) {
        Dimension size = driver.manage().window().getSize();
        int endx = (int) (size.width * 0.95);
        int startx = (int) (size.width * 0.05);
        int starty = size.height / 2;

        for (int i = 0; i <= howMany; i++) {
            scroll(startx, starty, endx, starty);
        }
    }

    public void swipeUp(int howMany) {
        Dimension size = driver.manage().window().getSize();
        int endy = (int) (size.height * 0.20);
        int starty = (int) (size.height * 0.80);
        int startx = size.width / 2;
            scroll(startx, starty, startx, endy);
    }


    public String returnText(MobileElement element) {
        waitForElement(element);
        return element.getAttribute("text");
    }

    public boolean isPresent(MobileElement element) {
        try {
            element.isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void takeScreenShot(String screenName) throws IOException {
        String Screenshotpath = System.getProperty("user.dir") + "\\resources\\screenshots\\";
        File scrFile = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(Screenshotpath + screenName + ".jpg"));
    }

    public String takeScreenshotandReturnPath(String screenName) throws IOException {
        String Screenshotpath = System.getProperty("user.dir") + "\\resources\\screenshots\\";
        takeScreenShot(screenName);
        return Screenshotpath + screenName + ".jpg";
    }


}
