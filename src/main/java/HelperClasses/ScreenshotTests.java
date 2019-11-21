package HelperClasses;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.WebDriverRunner;
import com.google.common.io.Files;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class ScreenshotTests {

    @Attachment(type = "image/png")
    public static byte[] screenshot() {
        File screenshot = Screenshots.getLastScreenshot();
        try {
            return screenshot == null ? null : Files.toByteArray(screenshot);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //return ((TakesScreenshot) WebDriverRunner.getWebDriver()).getScreenshotAs(OutputType.BYTES);
    }

}
